package com.taskflow.repository;

import com.taskflow.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository
        extends JpaRepository<Note, Long> {

    List<Note> findByUserIdOrderByPinnedDescCreatedAtDesc(Long userId);
    List<Note> findByUserIdAndPinned(Long userId, boolean pinned);
}