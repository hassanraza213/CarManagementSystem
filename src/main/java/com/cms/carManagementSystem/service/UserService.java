package com.cms.carManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cms.carManagementSystem.dto.UserDTO;
import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.repository.UserRepo;

@Slf4j
@Service
public class UserService {

    private final UserRepo userRepo;

    @Qualifier("modelMapper")
    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating a new user with details: {}", userDTO);
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepo.save(user);
        log.info("User created successfully with ID: {}", savedUser.getUserId());
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);
        User updateUser = userRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new EntityNotFoundException("User not found with id " + id);
                });
        updateUser.setUserName(userDTO.getUserName());
        updateUser.setPassword(userDTO.getPassword());
        updateUser.setDescription(userDTO.getDescription());
        User updatedUser = userRepo.save(updateUser);
        log.info("User updated successfully with ID: {}", updatedUser.getUserId());
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<User> allUsers = userRepo.findAll();
        log.info("Total users fetched: {}", allUsers.size());
        return allUsers.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = userRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new EntityNotFoundException("User not found with id " + id);
                });
        log.info("User fetched successfully with ID: {}", user.getUserId());
        return modelMapper.map(user, UserDTO.class);
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        User delUser = userRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new EntityNotFoundException("User not found with id " + id);
                });
        userRepo.delete(delUser);
        log.info("User deleted successfully with ID: {}", id);
    }
}
