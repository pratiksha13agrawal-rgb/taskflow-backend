package com.taskflow.dto.request;

import lombok.Data;

@Data
public class HabitRequest {
    private String name;
    private String description;
    private String icon;
    private String color;
    private String bgColor;
    private String frequency;
    private String category;
    private Integer targetDays;
}
