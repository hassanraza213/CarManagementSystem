package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.PermissionsDTO;
import com.cms.carManagementSystem.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
public class PermissionsController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PermissionsDTO> createPermissions(@RequestBody PermissionsDTO permissionsDTO) {
        log.info("Creating new permission with name: {}", permissionsDTO.getPermissionName());
        PermissionsDTO createdPermission = permissionService.createPermissions(permissionsDTO);
        return ResponseEntity.ok(createdPermission);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PermissionsDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionsDTO permissionsDTO) {
        log.info("updating the permission with id: {}", id);
        PermissionsDTO updatedPermission = permissionService.updatePermission(id, permissionsDTO);
        return ResponseEntity.ok(updatedPermission);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PermissionsDTO> getPermissionById(@PathVariable Long id) {
        log.info("Fetching permissiong with id: {}", id);
        PermissionsDTO gettingPermissionById = permissionService.getPermissionById(id);
        return ResponseEntity.ok(gettingPermissionById);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PermissionsDTO>> getAllPermission() {
        log.info("Fetching all permissions");
        List<PermissionsDTO> gettingAllPermissions = permissionService.getAllPermission();
        return ResponseEntity.ok(gettingAllPermissions);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deletePermission(@PathVariable Long id) {
        log.info("deleting permission with id: {}", id);
        permissionService.deletePermission(id);
        return ResponseEntity.ok("Permission deleted successfully with id: " + id);
    }
}
