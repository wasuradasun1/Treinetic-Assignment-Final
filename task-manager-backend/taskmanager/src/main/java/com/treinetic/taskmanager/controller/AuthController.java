package com.treinetic.taskmanager.controller;

import com.treinetic.taskmanager.dto.AuthRequest;
import com.treinetic.taskmanager.dto.AuthResponse;
import com.treinetic.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling user authentication and registration.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Registers a new user with the provided authentication details.
     *
     * @param request The registration request containing user credentials.
     * @return An {@link AuthResponse} containing authentication details (e.g., JWT token).
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates an existing user with the provided credentials.
     *
     * @param request The authentication request containing user credentials.
     * @return An {@link AuthResponse} containing authentication details (e.g., JWT token).
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}