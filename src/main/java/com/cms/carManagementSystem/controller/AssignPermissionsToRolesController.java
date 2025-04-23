package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.AssignPermissionsToRolesDTO;
import com.cms.carManagementSystem.service.AssignPermissionsToRolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/permission-to-roles")
public class AssignPermissionsToRolesController {

    @Autowired
    private AssignPermissionsToRolesService permissionsToRolesService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AssignPermissionsToRolesDTO> createPermissionsToRoles(@RequestBody AssignPermissionsToRolesDTO permissionsToRolesDTO) {
        log.info("Assigning permission with id: {} to role with id: {}", permissionsToRolesDTO.getPermissionId(),
                permissionsToRolesDTO.getRoleId());

        AssignPermissionsToRolesDTO createdPermissionsToRolesDTO = permissionsToRolesService
                .createPermissionsToRoles(permissionsToRolesDTO);
        return ResponseEntity.ok(createdPermissionsToRolesDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AssignPermissionsToRolesDTO> updatePermissionToRoles(@PathVariable Long id,
                                                                               @RequestBody AssignPermissionsToRolesDTO permissionsToRolesDTO) {
        log.info("Updating permission role with id: {}", id);
        AssignPermissionsToRolesDTO updatedPermissionsToRolesDTO = permissionsToRolesService.updatePermissionsToRoles(id,
                permissionsToRolesDTO);
        return ResponseEntity.ok(updatedPermissionsToRolesDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AssignPermissionsToRolesDTO> getPermissionsToRolesById(@PathVariable Long id) {
        log.info("Fetching permission role with id: {}", id);
        AssignPermissionsToRolesDTO getPermissionsToRolesDTO = permissionsToRolesService.getPermissionsToRolesById(id);
        return ResponseEntity.ok(getPermissionsToRolesDTO);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AssignPermissionsToRolesDTO>> getAllPermissionToRoles() {
        log.info("Fetching all the permissions to roles");
        List<AssignPermissionsToRolesDTO> getAllPermissionsToRolesDTO = permissionsToRolesService.getAllPermissionToRoles();
        return ResponseEntity.ok(getAllPermissionsToRolesDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deletePermissionsToRoles(@PathVariable Long id) {
        log.info("Deleting permission to role with id: {}", id);
        permissionsToRolesService.deletePermissionsToRoles(id);
        return ResponseEntity.ok("Permission to role deleted successfully with id: " + id);
    }
}
