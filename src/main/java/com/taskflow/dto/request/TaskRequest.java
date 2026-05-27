package com.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequest {

    @NotBlank
    private String title;

    private String description;
    private String priority;
    private String status;
    private String category;
    private LocalDateTime dueDate;
    private boolean done;
    private String tags;
}