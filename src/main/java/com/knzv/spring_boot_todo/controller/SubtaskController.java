package com.knzv.spring_boot_todo.controller;

import com.knzv.spring_boot_todo.dto.SubtaskRequest;
import com.knzv.spring_boot_todo.dto.SubtaskResponse;
import com.knzv.spring_boot_todo.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/subtask/")
@RestController
public class SubtaskController {
    @Autowired
    private SubtaskService subtaskService;

    @PostMapping("{parentTaskId}/create")
    public ResponseEntity<?> createSubtask(@PathVariable Long parentTaskId, @RequestBody SubtaskRequest request) {
        SubtaskResponse subtaskResponse = subtaskService.create(parentTaskId, request);

        return new ResponseEntity<>(subtaskResponse, HttpStatus.CREATED);
    }

    @GetMapping("{parentTaskId}")
    public ResponseEntity<?> getAllSubtasks(@PathVariable Long parentTaskId) {
        List<SubtaskResponse> subtasks = subtaskService.getAllSubtasks(parentTaskId);

        return new ResponseEntity<>(subtasks, HttpStatus.OK);
    }

    @DeleteMapping("delete/{subtaskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long subtaskId) {
        subtaskService.deleteSubtaskById(subtaskId);

        return new ResponseEntity<>("Подзадача удалена", HttpStatus.OK);
    }

    @PutMapping("complete/{id}")
    public ResponseEntity<?> setSubtaskCheckedById(@PathVariable Long id) {
        SubtaskResponse response = subtaskService.setSubtaskCheckedById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateSubtask(@RequestBody SubtaskRequest request, @PathVariable Long id) {
        SubtaskResponse response = subtaskService.updateSubtask(id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
