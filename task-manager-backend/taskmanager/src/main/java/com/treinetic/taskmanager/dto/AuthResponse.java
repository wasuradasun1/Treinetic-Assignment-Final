package com.treinetic.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a response returned after successful authentication.
 * <p>
 * This class encapsulates the authentication token and user-related
 * information that can be returned to the client.
 * </p>
 *
 * <p>
 * Lombok annotations used:
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString methods</li>
 *   <li>{@code @Builder} - Enables the builder pattern</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields</li>
 * </ul>
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String username;
}