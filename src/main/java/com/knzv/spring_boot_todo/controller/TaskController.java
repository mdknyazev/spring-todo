package com.knzv.spring_boot_todo.controller;
import com.knzv.spring_boot_todo.dto.TaskRequest;
import com.knzv.spring_boot_todo.dto.TaskResponse;
import com.knzv.spring_boot_todo.model.Task;
import com.knzv.spring_boot_todo.repository.SubtaskRepository;
import com.knzv.spring_boot_todo.service.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/task")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private SubtaskRepository subtaskRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest request) {
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

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {

        subtaskRepository.deleteByTaskId(id);

        taskService.deleteTaskById(id);
        return new ResponseEntity<>("Задача удалена", HttpStatus.OK);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> setTaskCheckedById(@PathVariable Long id) {
        TaskResponse response = taskService.setTaskCheckedById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskRequest request, @PathVariable Long id) {

        TaskResponse response = taskService.updateTaskById(id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
