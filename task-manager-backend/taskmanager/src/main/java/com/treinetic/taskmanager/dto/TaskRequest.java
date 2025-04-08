package com.treinetic.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents the data required to create or update a task.
 * <p>
 * This class is used for receiving task-related input from the client
 * in create/update API requests.
 * </p>
 *
 * <p>
 * Validation is enforced to ensure that important fields are properly provided:
 * <ul>
 *   <li>{@code title} must be non-blank and not exceed 100 characters</li>
 *   <li>{@code description} is optional but cannot exceed 500 characters</li>
 *   <li>{@code status} must be non-blank</li>
 * </ul>
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
public class TaskRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
    @NotBlank(message = "Status is required")
    private String status;
}