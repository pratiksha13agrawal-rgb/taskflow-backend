package com.taskflow.service;

import com.taskflow.dto.request.PasswordRequest;
import com.taskflow.dto.request.ProfileRequest;
import com.taskflow.entity.User;
import com.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getProfile(String email) {
        return getUser(email);
    }

    public User updateProfile(ProfileRequest req, String email) {
        User user = getUser(email);
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setBio(req.getBio());
        user.setPhone(req.getPhone());
        user.setLocation(req.getLocation());
        user.setWebsite(req.getWebsite());
        if (req.getAvatar() != null) {
            user.setAvatar(req.getAvatar());
        }
        return userRepository.save(user);
    }

    public void changePassword(
            PasswordRequest req, String email) {
        User user = getUser(email);

        if (!passwordEncoder.matches(
                req.getCurrentPassword(),
                user.getPassword())) {
            throw new RuntimeException(
                "Current password is incorrect");
        }

        user.setPassword(
            passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() ->
                new RuntimeException("User not found"));
    }
}