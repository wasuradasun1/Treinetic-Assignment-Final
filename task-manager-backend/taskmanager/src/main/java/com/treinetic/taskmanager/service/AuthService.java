package com.treinetic.taskmanager.service;

import com.treinetic.taskmanager.dto.AuthRequest;
import com.treinetic.taskmanager.dto.AuthResponse;
import com.treinetic.taskmanager.model.User;
import com.treinetic.taskmanager.repository.UserRepository;
import com.treinetic.taskmanager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling authentication and registration logic.
 * <p>
 * This service interacts with the {@link UserRepository}, encodes passwords,
 * generates JWT tokens, and authenticates user credentials using Spring Security.
 * </p>
 *
 * <p>
 * Dependencies are injected via constructor using {@code @RequiredArgsConstructor}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user based on the provided {@link AuthRequest}.
     * <p>
     * The password is securely encoded, and a JWT token is generated for the new user.
     * </p>
     *
     * @param request the registration request containing username and password
     * @return an {@link AuthResponse} containing the generated JWT token and user details
     */
    public AuthResponse register(AuthRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    /**
     * Authenticates a user with the provided credentials.
     * <p>
     * Uses the {@link AuthenticationManager} to verify credentials and issues a JWT token on success.
     * </p>
     *
     * @param request the authentication request containing username and password
     * @return an {@link AuthResponse} containing the generated JWT token and user details
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     */
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }
}