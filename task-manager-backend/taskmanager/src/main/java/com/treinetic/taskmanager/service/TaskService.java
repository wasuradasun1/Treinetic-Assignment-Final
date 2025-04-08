package com.treinetic.taskmanager.service;

import com.treinetic.taskmanager.dto.TaskRequest;
import com.treinetic.taskmanager.dto.TaskResponse;
import com.treinetic.taskmanager.exception.ResourceNotFoundException;
import com.treinetic.taskmanager.model.Task;
import com.treinetic.taskmanager.model.User;
import com.treinetic.taskmanager.repository.TaskRepository;
import com.treinetic.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling task-related business logic.
 * <p>
 * Provides methods for creating, retrieving, updating, and deleting tasks,
 * as well as enforcing user-based access control.
 * </p>
 *
 * <p>
 * This service ensures that users can only access and modify their own tasks.
 * </p>
 *
 * <p>
 * Dependencies:
 * <ul>
 *     <li>{@link TaskRepository} - for task database operations</li>
 *     <li>{@link UserRepository} - for fetching user details</li>
 *     <li>{@link ModelMapper} - for mapping between DTOs and entities</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * Retrieves all tasks belonging to the currently authenticated user.
     *
     * @return a list of {@link TaskResponse} objects
     */
    public List<TaskResponse> getAllTasksForCurrentUser() {
        User user = getCurrentUser();
        return taskRepository.findByUserId(user.getId()).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific task by its ID if it belongs to the current user.
     *
     * @param id the ID of the task
     * @return the corresponding {@link TaskResponse}
     * @throws ResourceNotFoundException if the task is not found
     * @throws AccessDeniedException if the task does not belong to the current user
     */
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        verifyTaskOwnership(task);

        return convertToResponse(task);
    }

    /**
     * Creates a new task for the currently authenticated user.
     *
     * @param taskRequest the task data from the client
     * @return the created {@link TaskResponse}
     */
    public TaskResponse createTask(TaskRequest taskRequest) {
        User user = getCurrentUser();

        Task task = modelMapper.map(taskRequest, Task.class);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return convertToResponse(savedTask);
    }

    /**
     * Updates an existing task if it belongs to the current user.
     *
     * @param id the ID of the task to update
     * @param taskRequest the new task data
     * @return the updated {@link TaskResponse}
     * @throws ResourceNotFoundException if the task is not found
     * @throws AccessDeniedException if the task does not belong to the current user
     */
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        verifyTaskOwnership(existingTask);

        modelMapper.map(taskRequest, existingTask);
        Task updatedTask = taskRepository.save(existingTask);
        return convertToResponse(updatedTask);
    }

    /**
     * Deletes a task by its ID if it belongs to the current user.
     *
     * @param id the ID of the task to delete
     * @throws ResourceNotFoundException if the task is not found
     * @throws AccessDeniedException if the task does not belong to the current user
     */
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        verifyTaskOwnership(task);

        taskRepository.delete(task);
    }

    /**
     * Converts a {@link Task} entity to a {@link TaskResponse} DTO,
     * including user information.
     *
     * @param task the task entity
     * @return the corresponding {@link TaskResponse}
     */
    private TaskResponse convertToResponse(Task task) {
        TaskResponse response = modelMapper.map(task, TaskResponse.class);
        response.setUserId(task.getUser().getId());
        response.setUsername(task.getUser().getUsername());
        return response;
    }

    /**
     * Retrieves the currently authenticated user based on the security context.
     *
     * @return the current {@link User}
     * @throws ResourceNotFoundException if the user is not found
     */
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Verifies that the task belongs to the currently authenticated user.
     *
     * @param task the task to verify
     * @throws AccessDeniedException if the task is owned by another user
     */
    private void verifyTaskOwnership(Task task) {
        User currentUser = getCurrentUser();
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You don't have permission to access this task");
        }
    }
}