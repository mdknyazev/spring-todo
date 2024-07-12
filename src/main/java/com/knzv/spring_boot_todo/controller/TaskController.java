package com.knzv.spring_boot_todo.controller;

import com.knzv.spring_boot_todo.dto.TaskDto;
import com.knzv.spring_boot_todo.model.Task;
import com.knzv.spring_boot_todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/task")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDto request) {
        Task task = taskService.createTask(request);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }
}
