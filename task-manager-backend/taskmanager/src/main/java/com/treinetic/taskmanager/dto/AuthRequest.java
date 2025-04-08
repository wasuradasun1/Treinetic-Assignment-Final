package com.treinetic.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents an authentication request containing user credentials.
 * <p>
 * This class is used to encapsulate the username and password
 * provided by a client during the authentication process.
 * </p>
 *
 * <p>
 * Validation annotations are included to ensure that both fields
 * are not blank.
 * </p>
 *
 * <p>
 * Lombok annotations are used to generate boilerplate code:
 * - {@code @Data} for getters, setters, toString, equals, and hashCode
 * - {@code @Builder} for the builder pattern
 * - {@code @AllArgsConstructor} and {@code @NoArgsConstructor} for constructors
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}