package com.knzv.spring_boot_todo.service;

import com.knzv.spring_boot_todo.dto.TaskRequest;
import com.knzv.spring_boot_todo.dto.TaskResponse;
import com.knzv.spring_boot_todo.exception.InvalidTaskException;
import com.knzv.spring_boot_todo.exception.TaskNotExistsException;
import com.knzv.spring_boot_todo.model.Task;
import com.knzv.spring_boot_todo.model.User;
import com.knzv.spring_boot_todo.repository.TaskRepository;
import com.knzv.spring_boot_todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public Task createTask(TaskRequest request) {

        if (request.getText().isEmpty()) {
            throw new InvalidTaskException("Обязательное поле текст");
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь с такой почтой не найден"));

        Task task = Task.builder()
                .text(request.getText())
                .description(request.getDescription())
                .status(false)
                .creationDate(LocalDate.now())
                .deadlineDate(request.getDeadlineDate())
                .user(user)
                .build();

        taskRepository.save(task);
        return task;
    }

    public List<TaskResponse> getAllTasks() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Пользователь с такой почтой не найден")
                );

        List<Task> tasksList = taskRepository.findByUserId(user.getId());

        return tasksList.stream()
                .map(taskEntity -> TaskResponse.builder()
                        .id(taskEntity.getId())
                        .text(taskEntity.getText())
                        .status(taskEntity.isStatus())
                        .description(taskEntity.getDescription())
                        .creationDate(taskEntity.getCreationDate())
                        .deadlineDate(taskEntity.getDeadlineDate())
                        .userEmail(taskEntity.getUser().getEmail())
                        .build()
                ).collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Пользователь с такой почтой не найден")
                );

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotExistsException("Задача не найдена"));
        TaskResponse response = TaskResponse.builder()
                .id(task.getId())
                .text(task.getText())
                .description(task.getDescription())
                .status(task.isStatus())
                .creationDate(task.getCreationDate())
                .deadlineDate(task.getDeadlineDate())
                .userEmail(task.getUser().getEmail())
                .build();
        return response;
    }

    public TaskResponse setTaskCheckedById(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Пользователь с такой почтой не найден")
                );

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotExistsException("Задача не найдена"));

        task.setStatus(!task.isStatus());

        task = taskRepository.save(task);

        TaskResponse taskResponse = TaskResponse.builder()
                .id(task.getId())
                .text(task.getText())
                .status(task.isStatus())
                .userEmail(user.getEmail())
                .build();
        return taskResponse;
    }

    public TaskResponse updateTaskById(Long id, TaskRequest request) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Пользователь с такой почтой не найден")
                );

        if (request.getText().isEmpty()) {
            throw new InvalidTaskException("Обязательное поле текст");
        }

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotExistsException("Задача не найдена"));
        task.setText(request.getText());
        task.setDescription(request.getDescription());
        task.setDeadlineDate(request.getDeadlineDate());

        task = taskRepository.save(task);

        TaskResponse taskResponse = TaskResponse.builder()
                .id(task.getId())
                .text(task.getText())
                .description(task.getDescription())
                .status(task.isStatus())
                .creationDate(task.getCreationDate())
                .deadlineDate(task.getDeadlineDate())
                .build();
        return taskResponse;
    }

    public void deleteTaskById(Long id) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Пользователь с такой почтой не найден")
                );

        taskRepository.deleteByIdAndUserId(id, user.getId());
    }
}
