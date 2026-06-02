package com.taskflow.dto.request;

import lombok.Data;

@Data
public class TeamMemberRequest {
    private String email;
    private String role;
}