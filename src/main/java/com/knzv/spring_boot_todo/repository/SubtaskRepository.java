package com.knzv.spring_boot_todo.repository;

import com.knzv.spring_boot_todo.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findByTaskId(Long id);
    void deleteByTaskId(Long taskId);
}
