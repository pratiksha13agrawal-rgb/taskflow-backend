package com.taskflow.dto.request;

import lombok.Data;

@Data
public class NoteRequest {
    private String title;
    private String content;
    private String color;
    private boolean pinned;
    private String tags;
}
