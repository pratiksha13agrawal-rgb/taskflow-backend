package com.taskflow.dto.request;

import lombok.Data;

@Data
public class ProfileRequest {
    private String name;
    private String email;
    private String bio;
    private String phone;
    private String location;
    private String website;
    private String avatar;
}