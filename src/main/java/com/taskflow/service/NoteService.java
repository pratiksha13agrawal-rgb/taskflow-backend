package com.taskflow.service;

import com.taskflow.dto.request.NoteRequest;
import com.taskflow.entity.Note;
import com.taskflow.entity.User;
import com.taskflow.repository.NoteRepository;
import com.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public List<Note> getAllNotes(String email) {
        User user = getUser(email);
        return noteRepository
                .findByUserIdOrderByPinnedDescCreatedAtDesc(
                        user.getId());
    }

    public Note createNote(NoteRequest req, String email) {
        User user = getUser(email);
        Note note = Note.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .color(req.getColor())
                .pinned(req.isPinned())
                .tags(req.getTags())
                .user(user)
                .build();
        return noteRepository.save(note);
    }

    public Note updateNote(
            Long id, NoteRequest req, String email) {
        Note note = getNoteById(id, email);
        note.setTitle(req.getTitle());
        note.setContent(req.getContent());
        note.setColor(req.getColor());
        note.setPinned(req.isPinned());
        note.setTags(req.getTags());
        return noteRepository.save(note);
    }
    public void deleteNote(Long id, String email) {
        Note note = getNoteById(id, email);
        noteRepository.delete(note);
    }

    public Note togglePin(Long id, String email) {
        Note note = getNoteById(id, email);
        note.setPinned(!note.isPinned());
        return noteRepository.save(note);
    }

    private Note getNoteById(Long id, String email) {
        User user = getUser(email);
        return noteRepository.findById(id)
                .filter(n ->
                        n.getUser().getId().equals(user.getId()))
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public List<Note> searchNotes(String query, String email) {
        User user = getUser(email);
        if (query == null || query.trim().isEmpty()) {
            return noteRepository
                .findByUserIdOrderByPinnedDescCreatedAtDesc(
                    user.getId());
        }
        return noteRepository.searchNotes(
            user.getId(), query.trim());
    }

}
