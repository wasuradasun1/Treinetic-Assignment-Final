package com.treinetic.taskmanager.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents the response returned for a task.
 * <p>
 * This class is used to send task-related information back to the client,
 * typically after creating, updating, or retrieving tasks.
 * </p>
 *
 * <p>
 * Lombok annotations used:
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, equals, hashCode, and toString</li>
 *   <li>{@code @NoArgsConstructor} - No-argument constructor</li>
 *   <li>{@code @AllArgsConstructor} - All-argument constructor</li>
 *   <li>{@code @Builder} - Enables the builder pattern</li>
 * </ul>
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
}