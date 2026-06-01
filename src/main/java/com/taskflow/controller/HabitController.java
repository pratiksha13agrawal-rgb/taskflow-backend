package com.taskflow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.dto.request.HabitRequest;
import com.taskflow.dto.response.ApiResponse;
import com.taskflow.entity.Habit;
import com.taskflow.service.HabitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Habit>>> getAll(
            @AuthenticationPrincipal UserDetails user) {
        List<Habit> habits =
            habitService.getAllHabits(user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success(habits));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Habit>> create(
            @RequestBody HabitRequest req,
            @AuthenticationPrincipal UserDetails user) {
        Habit habit =
            habitService.createHabit(req, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Habit created", habit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Habit>> update(
            @PathVariable Long id,
            @RequestBody HabitRequest req,
            @AuthenticationPrincipal UserDetails user) {
        Habit habit =
            habitService.updateHabit(
                id, req, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Habit updated", habit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {
        habitService.deleteHabit(id, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Habit deleted", null));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<Habit>> toggleToday(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {
        Habit habit =
            habitService.toggleToday(id, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Habit updated", habit));
    }
}
