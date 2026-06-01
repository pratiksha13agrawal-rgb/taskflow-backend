package com.taskflow.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "habits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String icon;
    private String color;

    @Column(name = "bg_color")
    private String bgColor;

    private String frequency;
    private String category;

    @Column(name = "target_days")
    private Integer targetDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"habits", "tasks", "notes", "password", "active", "createdAt", "updatedAt", "hibernateLazyInitializer"})
    private User user;

    @OneToMany(mappedBy = "habit",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"habit", "hibernateLazyInitializer"})
    private List<HabitEntry> entries = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}