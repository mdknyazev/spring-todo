package com.knzv.spring_boot_todo.service;

import com.knzv.spring_boot_todo.dto.TaskDto;
import com.knzv.spring_boot_todo.dto.TaskResponse;
import com.knzv.spring_boot_todo.model.Task;
import com.knzv.spring_boot_todo.model.User;
import com.knzv.spring_boot_todo.repository.TaskRepository;
import com.knzv.spring_boot_todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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


    public Task createTask(TaskDto request) {
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
        List<Task> tasksList = taskRepository.findAll();

        return tasksList.stream()
                .map(taskEntity -> TaskResponse.builder()
                        .text(taskEntity.getText())
                        .status(taskEntity.isStatus())
                        .description(taskEntity.getDescription())
                        .creationDate(taskEntity.getCreationDate())
                        .deadlineDate(taskEntity.getDeadlineDate())
                        .build()
                ).collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NullPointerException("Задача не найдена"));
        TaskResponse response = TaskResponse.builder()
                .text(task.getText())
                .description(task.getDescription())
                .status(task.isStatus())
                .creationDate(task.getCreationDate())
                .deadlineDate(task.getDeadlineDate())
                .build();
        return response;
    }
}
