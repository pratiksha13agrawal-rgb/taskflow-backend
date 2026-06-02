package com.taskflow.dto.request;

import lombok.Data;

@Data
public class WorkspaceRequest {
    private String name;
    private String description;
}