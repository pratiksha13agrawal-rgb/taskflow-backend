package com.taskflow.service;

import com.taskflow.dto.request.LoginRequest;
import com.taskflow.dto.request.RegisterRequest;
import com.taskflow.dto.response.AuthResponse;
import com.taskflow.entity.User;
import com.taskflow.repository.UserRepository;
import com.taskflow.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository       userRepository;
    private final PasswordEncoder      passwordEncoder;
    private final JwtTokenProvider     jwtTokenProvider;
    private final AuthenticationManager authManager;

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(req.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String token =
                jwtTokenProvider.generateToken(user.getEmail());

        return buildResponse(user, token);
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException(
                    "Email already registered");
        }

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(
                        req.getPassword()))
                .active(true)
                .build();

        userRepository.save(user);

        String token =
                jwtTokenProvider.generateToken(user.getEmail());

        return buildResponse(user, token);
    }

    private AuthResponse buildResponse(
            User user, String token) {

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .user(AuthResponse.UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .avatar(user.getAvatar())
                        .build())
                .build();
    }
}