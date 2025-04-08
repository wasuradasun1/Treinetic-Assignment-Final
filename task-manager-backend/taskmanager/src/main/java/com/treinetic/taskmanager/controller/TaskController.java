package com.treinetic.taskmanager.controller;

import com.treinetic.taskmanager.dto.TaskRequest;
import com.treinetic.taskmanager.dto.TaskResponse;
import com.treinetic.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tasks.
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    /**
     * Get all tasks for the currently authenticated user.
     *
     * @return A list of TaskResponse objects representing the user's tasks.
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasksForCurrentUser());
    }

    /**
     * Get a specific task by its ID.
     *
     * @param id The ID of the task to retrieve.
     * @return A TaskResponse object representing the task.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    /**
     * Create a new task for the current user.
     *
     * @param taskRequest The request body containing task data.
     * @return The created TaskResponse object with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRequest));
    }

    /**
     * Update an existing task by its ID.
     *
     * @param id          The ID of the task to update.
     * @param taskRequest The request body containing updated task data.
     * @return The updated TaskResponse object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequest));
    }

    /**
     * Delete a task by its ID.
     *
     * @param id The ID of the task to delete.
     * @return A ResponseEntity with HTTP status 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}