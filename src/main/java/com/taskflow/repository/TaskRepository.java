package com.taskflow.repository;

import com.taskflow.entity.Task;
import com.taskflow.entity.Task.Priority;
import com.taskflow.entity.Task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Task> findByUserIdAndPriority(Long userId, Priority priority);
    List<Task> findByUserIdAndStatus(Long userId, TaskStatus status);
    List<Task> findByUserIdAndDone(Long userId, boolean done);
    long countByUserIdAndDone(Long userId, boolean done);
}