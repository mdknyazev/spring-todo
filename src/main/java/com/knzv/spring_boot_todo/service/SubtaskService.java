package com.knzv.spring_boot_todo.service;

import com.knzv.spring_boot_todo.dto.SubtaskRequest;
import com.knzv.spring_boot_todo.dto.SubtaskResponse;
import com.knzv.spring_boot_todo.exception.InvalidTaskException;
import com.knzv.spring_boot_todo.exception.TaskNotExistsException;
import com.knzv.spring_boot_todo.model.Subtask;
import com.knzv.spring_boot_todo.model.Task;
import com.knzv.spring_boot_todo.repository.SubtaskRepository;
import com.knzv.spring_boot_todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubtaskService {
    @Autowired
    private SubtaskRepository subtaskRepository;
    @Autowired
    private TaskRepository taskRepository;

    public SubtaskResponse create(Long taskParentId, SubtaskRequest request) {

        if (request.getText().isEmpty()) {
            throw new InvalidTaskException("Обязательное поле текст");
        }

        Task taskParent = taskRepository.findById(taskParentId)
                .orElseThrow(() -> new TaskNotExistsException("Подзадача не найдена"));

        Subtask subtask = Subtask.builder()
                .text(request.getText())
                .status(false)
                .task(taskParent)
                .build();

        subtaskRepository.save(subtask);

        SubtaskResponse subtaskResponse = SubtaskResponse.builder()
                .id(subtask.getId())
                .text(subtask.getText())
                .status(false)
                .taskParentId(taskParentId)
                .build();
        return subtaskResponse;
    }

    public List<SubtaskResponse> getAllSubtasks(Long parentTaskId) {
        List<Subtask> subtasks = subtaskRepository.findByTaskId(parentTaskId);

        return subtasks.stream()
                .map(subtaskEntity ->
                                SubtaskResponse.builder()
                                        .id(subtaskEntity.getId())
                                        .text(subtaskEntity.getText())
                                        .status(subtaskEntity.isStatus())
                                        .taskParentId(parentTaskId)
                                        .build()
                        ).collect(Collectors.toList());
    }

    public void deleteSubtaskById(Long subtaskId) {
        subtaskRepository.deleteById(subtaskId);
    }

    public SubtaskResponse setSubtaskCheckedById(Long id) {
        Subtask subtask = subtaskRepository.findById(id)
                .orElseThrow(() -> new TaskNotExistsException("Подзадача не найдена"));

        subtask.setStatus(!subtask.isStatus());

        subtask = subtaskRepository.save(subtask);

        SubtaskResponse response = SubtaskResponse.builder()
                .id(subtask.getId())
                .text(subtask.getText())
                .status(subtask.isStatus())
                .taskParentId(subtask.getId())
                .build();
        return response;
    }

    public SubtaskResponse updateSubtask(Long id, SubtaskRequest request) {
        if (request.getText().isEmpty()) {
            throw new InvalidTaskException("Обязательное поле текст");
        }

        Subtask subtask = subtaskRepository.findById(id)
                .orElseThrow(() -> new TaskNotExistsException("Подзадача не найдена"));

        subtask.setText(request.getText());

        subtask = subtaskRepository.save(subtask);

        SubtaskResponse response = SubtaskResponse.builder()
                .id(subtask.getId())
                .text(subtask.getText())
                .status(subtask.isStatus())
                .taskParentId(subtask.getTask().getId())
                .build();
        return response;
    }
}
