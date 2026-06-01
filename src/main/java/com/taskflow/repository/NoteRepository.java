package com.taskflow.repository;

import com.taskflow.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository
        extends JpaRepository<Note, Long> {

    List<Note> findByUserIdOrderByPinnedDescCreatedAtDesc(Long userId);
    List<Note> findByUserIdAndPinned(Long userId, boolean pinned);
    @Query("SELECT n FROM Note n WHERE n.user.id = :userId " +
       "AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
       "OR LOWER(n.content) LIKE LOWER(CONCAT('%', :query, '%')))" +
       "ORDER BY n.pinned DESC, n.createdAt DESC")
    List<Note> searchNotes(@Param("userId") Long userId, @Param("query") String query);
}