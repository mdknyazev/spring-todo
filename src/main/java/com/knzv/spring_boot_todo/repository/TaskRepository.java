package com.knzv.spring_boot_todo.repository;

import com.knzv.spring_boot_todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
