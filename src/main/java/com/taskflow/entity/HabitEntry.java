package com.taskflow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "habit_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    @JsonIgnoreProperties({"entries", "user", "hibernateLazyInitializer"})
    private Habit habit;
}