package com.taskflow.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workspaces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"password", "tasks", "notes",
        "habits", "active", "createdAt", "updatedAt", "hibernateLazyInitializer"})
    private User owner;

    @OneToMany(mappedBy = "workspace",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"workspace",
        "hibernateLazyInitializer"})
    private List<TeamMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "workspace",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"workspace",
        "hibernateLazyInitializer"})
    private List<TeamTask> teamTasks = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}