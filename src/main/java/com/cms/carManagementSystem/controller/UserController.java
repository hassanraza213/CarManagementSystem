package com.cms.carManagementSystem.controller;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cms.carManagementSystem.dto.UserDTO;
import com.cms.carManagementSystem.service.UserService;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Creating new user: {}", userDTO);
        UserDTO createdUser = userService.createUser(userDTO);
        log.info("User created successfully: {}", createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        log.info("Updating user with ID: {} and data: {}", id, userDTO);
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            log.info("User updated successfully: {}", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            log.error("User not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        try {
            UserDTO userDTO = userService.getUserById(id);
            log.info("User found: {}", userDTO);
            return ResponseEntity.ok(userDTO);
        } catch (EntityNotFoundException e) {
            log.error("User not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<UserDTO> users = userService.getAllUsers();
        log.info("Retrieved {} users", users.size());
        return users;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("Attempting to delete user with ID: {}", id);
        try {
            userService.deleteUser(id);
            log.info("User with ID {} deleted successfully", id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        } catch (EntityNotFoundException e) {
            log.error("Failed to delete user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");
        }
    }
}
