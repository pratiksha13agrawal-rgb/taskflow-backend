package com.taskflow.repository;

import com.taskflow.entity.TeamTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamTaskRepository extends JpaRepository<TeamTask, Long> {

    List<TeamTask> findByWorkspaceId(Long workspaceId);
}