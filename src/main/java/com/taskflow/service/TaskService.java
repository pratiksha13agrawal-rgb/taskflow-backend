package com.taskflow.service;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.entity.Task;
import com.taskflow.entity.User;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> getAllTasks(String email) {
        User user = getUser(email);
        return taskRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public Task createTask(
            TaskRequest req, String email) {

        User user = getUser(email);

        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .priority(Task.Priority.valueOf(
                        req.getPriority() != null
                                ? req.getPriority() : "medium"))
                .status(Task.TaskStatus.valueOf(
                        req.getStatus() != null
                                ? req.getStatus() : "pending"))
                .category(req.getCategory())
                .dueDate(req.getDueDate())
                .done(req.isDone())
                .tags(req.getTags())
                .user(user)
                .build();

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskRequest req, String email) {

        Task task = getTaskById(id, email);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        
        if (req.getPriority() != null) {
            task.setPriority(Task.Priority.valueOf(req.getPriority()));
        }
        if (req.getStatus() != null) {
           task.setStatus(Task.TaskStatus.valueOf(req.getStatus()));
        }
        
        task.setCategory(req.getCategory());
        task.setDueDate(req.getDueDate());
        task.setDone(req.isDone());
        task.setTags(req.getTags());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id, String email) {
        Task task = getTaskById(id, email);
        taskRepository.delete(task);
    }

    public Task toggleDone(Long id, String email) {
        Task task = getTaskById(id, email);
        task.setDone(!task.isDone());
        task.setStatus(task.isDone()
                ? Task.TaskStatus.completed
                : Task.TaskStatus.pending);
        return taskRepository.save(task);
    }

    private Task getTaskById(Long id, String email) {
        User user = getUser(email);
        return taskRepository.findById(id)
                .filter(t ->
                        t.getUser().getId()
                                .equals(user.getId()))
                .orElseThrow(() ->
                        new RuntimeException(
                                "Task not found"));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));
    }
}