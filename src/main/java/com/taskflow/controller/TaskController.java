package com.taskflow.controller;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.ApiResponse;
import com.taskflow.entity.Task;
import com.taskflow.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getAll(
            @AuthenticationPrincipal UserDetails user) {

        List<Task> tasks =
                taskService.getAllTasks(user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success(tasks));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> create(
            @Valid @RequestBody TaskRequest req,
            @AuthenticationPrincipal UserDetails user) {

        Task task =
                taskService.createTask(req, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Task created", task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest req,
            @AuthenticationPrincipal UserDetails user) {

        Task task = taskService.updateTask(
                id, req, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Task updated", task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {

        taskService.deleteTask(id, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Task deleted", null));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<Task>> toggle(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {

        Task task =
                taskService.toggleDone(id, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Task updated", task));
    }
}