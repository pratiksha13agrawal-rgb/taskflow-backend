package com.taskflow.service;

import com.taskflow.dto.request.*;
import com.taskflow.entity.*;
import com.taskflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final WorkspaceRepository   workspaceRepository;
    private final TeamMemberRepository  memberRepository;
    private final TeamTaskRepository    taskRepository;
    private final UserRepository        userRepository;

    // ── Workspace ─────────────────────────────────────────
    public Workspace getOrCreateWorkspace(String email) {
        User user = getUser(email);
        return workspaceRepository
            .findByOwnerId(user.getId())
            .stream()
            .findFirst()
            .orElseGet(() -> {
                Workspace ws = Workspace.builder()
                    .name(user.getName() + "'s Workspace")
                    .description("Team workspace")
                    .owner(user)
                    .build();
                return workspaceRepository.save(ws);
            });
    }

    public Workspace updateWorkspace(
            Long id, WorkspaceRequest req, String email) {
        User user = getUser(email);
        Workspace ws = workspaceRepository
            .findByIdAndOwnerId(id, user.getId())
            .orElseThrow(() ->
                new RuntimeException("Workspace not found"));
        ws.setName(req.getName());
        ws.setDescription(req.getDescription());
        return workspaceRepository.save(ws);
    }

    // ── Members ───────────────────────────────────────────
    public TeamMember inviteMember(
            Long wsId, TeamMemberRequest req,
            String email) {
        Workspace ws = workspaceRepository.findById(wsId)
            .orElseThrow(() ->
                new RuntimeException("Workspace not found"));

        if (memberRepository.existsByEmailAndWorkspaceId(
                req.getEmail(), wsId)) {
            throw new RuntimeException(
                "Member already activated");
        }

        TeamMember member = TeamMember.builder()
            .name(req.getEmail().split("@")[0])
            .email(req.getEmail())
            .role(req.getRole())
            .status("active")
            .avatar("linear-gradient(135deg,#9d8ef0,#f564a0)")
            .workspace(ws)
            .build();

        return memberRepository.save(member);
    }

    public TeamMember updateMemberRole(
            Long memberId, String role) {
        TeamMember member = memberRepository
            .findById(memberId)
            .orElseThrow(() ->
                new RuntimeException("Member not found"));
        member.setRole(role);
        return memberRepository.save(member);
    }

    public void removeMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // ── Team Tasks ────────────────────────────────────────
    public List<TeamTask> getTasks(Long wsId) {
        return taskRepository.findByWorkspaceId(wsId);
    }

    public TeamTask createTask(
            Long wsId, TeamTaskRequest req) {
        Workspace ws = workspaceRepository.findById(wsId)
            .orElseThrow(() ->
                new RuntimeException("Workspace not found"));

        TeamTask task = TeamTask.builder()
            .title(req.getTitle())
            .assigneeId(req.getAssigneeId())
            .priority(req.getPriority())
            .status(req.getStatus() != null
                ? req.getStatus() : "pending")
            .category(req.getCategory())
            .dueDate(req.getDueDate())
            .workspace(ws)
            .build();

        return taskRepository.save(task);
    }

    public TeamTask updateTaskStatus(
            Long taskId, String status) {
        TeamTask task = taskRepository.findById(taskId)
            .orElseThrow(() ->
                new RuntimeException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public TeamTask updateTask(
            Long taskId, TeamTaskRequest req) {
        TeamTask task = taskRepository.findById(taskId)
            .orElseThrow(() ->
                new RuntimeException("Task not found"));
        task.setTitle(req.getTitle());
        task.setAssigneeId(req.getAssigneeId());
        task.setPriority(req.getPriority());
        task.setStatus(req.getStatus());
        task.setCategory(req.getCategory());
        task.setDueDate(req.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() ->
                new RuntimeException("User not found"));
    }
}