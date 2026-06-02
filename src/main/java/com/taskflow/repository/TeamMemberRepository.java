package com.taskflow.repository;

import com.taskflow.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository
        extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByWorkspaceId(Long workspaceId);
    boolean existsByEmailAndWorkspaceId(String email, Long workspaceId);
}