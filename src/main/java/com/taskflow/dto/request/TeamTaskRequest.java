package com.taskflow.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TeamTaskRequest {
    private String title;
    private Long assigneeId;
    private String priority;
    private String status;
    private String category;
    private LocalDateTime dueDate;
}