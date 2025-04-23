package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.UserRolesDTO;
import com.cms.carManagementSystem.service.UserRolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user-roles")
public class UserRolesController {

    @Autowired
    private UserRolesService userRolesService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN)")
    public ResponseEntity<UserRolesDTO> createUserRoles(@RequestBody UserRolesDTO userRolesDTO) {

        log.info("Creating new User-role for userId: {}", userRolesDTO.getUserId());
        UserRolesDTO createdUserRole = userRolesService.createUserRoles(userRolesDTO);
        return ResponseEntity.ok(createdUserRole);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserRolesDTO> updateUserRoles(@PathVariable Long id, @RequestBody UserRolesDTO userRolesDTO) {

        log.info("Updating User-role with id : {}", id);
        UserRolesDTO updatedUserRole = userRolesService.updateUserRoles(id, userRolesDTO);
        return ResponseEntity.ok(updatedUserRole);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserRolesDTO> getUserRoleById(@PathVariable Long id) {
        log.info("Fetching User-role with the id : {}", id);
        UserRolesDTO gettingUserRoleById = userRolesService.getUserRoleById(id);
        return ResponseEntity.ok(gettingUserRoleById);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserRolesDTO>> getAllUserRole() {
        log.info("Fetching all User-roles");
        List<UserRolesDTO> userRolesDTOList = userRolesService.getAllUserRole();
        return ResponseEntity.ok(userRolesDTOList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserRole(Long id) {
        log.info("Deleting User-role with id: {}", id);
        userRolesService.deleteUserRole(id);
        return ResponseEntity.ok("Deleted user successfully with id: " + id);
    }
}
