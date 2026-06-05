package com.taskflow.dto.request;

import lombok.Data;

@Data
public class PasswordRequest {
    private String currentPassword;
    private String newPassword;
}