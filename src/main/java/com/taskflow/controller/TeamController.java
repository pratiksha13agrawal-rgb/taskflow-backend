package com.taskflow.controller;

import com.taskflow.dto.request.*;
import com.taskflow.dto.response.ApiResponse;
import com.taskflow.entity.*;
import com.taskflow.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    // ── Workspace ─────────────────────────────────────────
    @GetMapping("/workspace")
    public ResponseEntity<ApiResponse<Workspace>> getWorkspace(
            @AuthenticationPrincipal UserDetails user) {
        Workspace ws =
            teamService.getOrCreateWorkspace(
                user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success(ws));
    }

    @PutMapping("/workspace/{id}")
    public ResponseEntity<ApiResponse<Workspace>> updateWorkspace(
            @PathVariable Long id,
            @RequestBody WorkspaceRequest req,
            @AuthenticationPrincipal UserDetails user) {
        Workspace ws =
            teamService.updateWorkspace(
                id, req, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Workspace updated", ws));
    }

    // ── Members ───────────────────────────────────────────
    @PostMapping("/workspace/{wsId}/members")
    public ResponseEntity<ApiResponse<TeamMember>> invite(
            @PathVariable Long wsId,
            @RequestBody TeamMemberRequest req,
            @AuthenticationPrincipal UserDetails user) {
        TeamMember member =
            teamService.inviteMember(
                wsId, req, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Invitation sent", member));
    }

    @PatchMapping("/members/{memberId}/role")
    public ResponseEntity<ApiResponse<TeamMember>> updateRole(
            @PathVariable Long memberId,
            @RequestBody TeamMemberRequest req) {
        TeamMember member =
            teamService.updateMemberRole(
                memberId, req.getRole());
        return ResponseEntity.ok(
            ApiResponse.success("Role updated", member));
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<Void>> removeMember(
            @PathVariable Long memberId) {
        teamService.removeMember(memberId);
        return ResponseEntity.ok(
            ApiResponse.success("Member removed", null));
    }

    // ── Team Tasks ────────────────────────────────────────
    @GetMapping("/workspace/{wsId}/tasks")
    public ResponseEntity<ApiResponse<List<TeamTask>>> getTasks(
            @PathVariable Long wsId) {
        List<TeamTask> tasks =
            teamService.getTasks(wsId);
        return ResponseEntity.ok(
            ApiResponse.success(tasks));
    }

    @PostMapping("/workspace/{wsId}/tasks")
    public ResponseEntity<ApiResponse<TeamTask>> createTask(
            @PathVariable Long wsId,
            @RequestBody TeamTaskRequest req) {
        TeamTask task =
            teamService.createTask(wsId, req);
        return ResponseEntity.ok(
            ApiResponse.success("Task created", task));
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<ApiResponse<TeamTask>> updateTask(
            @PathVariable Long taskId,
            @RequestBody TeamTaskRequest req) {
        TeamTask task =
            teamService.updateTask(taskId, req);
        return ResponseEntity.ok(
            ApiResponse.success("Task updated", task));
    }

    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<ApiResponse<TeamTask>> updateStatus(
            @PathVariable Long taskId,
            @RequestBody TeamTaskRequest req) {
        TeamTask task =
            teamService.updateTaskStatus(
                taskId, req.getStatus());
        return ResponseEntity.ok(
            ApiResponse.success("Status updated", task));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
            @PathVariable Long taskId) {
        teamService.deleteTask(taskId);
        return ResponseEntity.ok(
            ApiResponse.success("Task deleted", null));
    }
}