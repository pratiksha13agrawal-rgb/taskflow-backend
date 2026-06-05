package com.taskflow.controller;

import com.taskflow.dto.request.PasswordRequest;
import com.taskflow.dto.request.ProfileRequest;
import com.taskflow.dto.response.ApiResponse;
import com.taskflow.entity.User;
import com.taskflow.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ApiResponse<User>> getProfile(
            @AuthenticationPrincipal UserDetails user) {
        User profile =
            profileService.getProfile(user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success(profile));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<User>> updateProfile(
            @RequestBody ProfileRequest req,
            @AuthenticationPrincipal UserDetails user) {
        User updated =
            profileService.updateProfile(
                req, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Profile updated", updated));
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @RequestBody PasswordRequest req,
            @AuthenticationPrincipal UserDetails user) {
        profileService.changePassword(
            req, user.getUsername());
        return ResponseEntity.ok(
            ApiResponse.success("Password changed", null));
    }
}