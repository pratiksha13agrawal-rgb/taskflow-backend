package com.taskflow.controller;

import com.taskflow.dto.request.NoteRequest;
import com.taskflow.dto.response.ApiResponse;
import com.taskflow.entity.Note;
import com.taskflow.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Note>>> getAll(
            @AuthenticationPrincipal UserDetails user) {
        List<Note> notes =
                noteService.getAllNotes(user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success(notes));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Note>> create(
            @RequestBody NoteRequest req,
            @AuthenticationPrincipal UserDetails user) {
        Note note =
                noteService.createNote(req, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Note created", note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Note>> update(
            @PathVariable Long id,
            @RequestBody NoteRequest req,
            @AuthenticationPrincipal UserDetails user) {
        Note note =
                noteService.updateNote(id, req, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Note updated", note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {
        noteService.deleteNote(id, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Note deleted", null));
    }

    @PatchMapping("/{id}/pin")
    public ResponseEntity<ApiResponse<Note>> togglePin(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {
        Note note =
                noteService.togglePin(id, user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Note updated", note));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Note>>> search(
            @RequestParam String query,
            @AuthenticationPrincipal UserDetails user) {
        List<Note> notes =
            noteService.searchNotes(query, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success(notes));
    }

}
