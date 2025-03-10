package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.UserDTO;
import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.repository.UserRepo;
import com.cms.carManagementSystem.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepo userRepo, @Qualifier("modelMapper") ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating a new user with details: {}", userDTO);
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepo.save(user);
        log.info("User created successfully with ID: {}", savedUser.getUserId());
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<User> allUsers = userRepo.findAll();
        log.info("Total users fetched: {}", allUsers.size());
        return allUsers.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    public UserDTO login(String userName, String password) {
        log.info("Attempting login for username : {}", userName);
        User user = userRepo.findByUserName(userName).orElseThrow(() -> {
            log.error("User not found with username : {}", userName);
            return new EntityNotFoundException("User not found with username " + userName);
        });
        if(!passwordEncoder.matches(password, user.getPassword())){
            log.error("Invalid password for username: {}", userName);
            throw new IllegalArgumentException("Invalid password");
        }
        log.info("Login successful for username: {}", userName);
        return modelMapper.map(user, UserDTO.class);
    }
}
