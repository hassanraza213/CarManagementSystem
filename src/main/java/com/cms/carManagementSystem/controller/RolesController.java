package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.RolesDTO;
import com.cms.carManagementSystem.service.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RolesDTO> createRole(@RequestBody RolesDTO rolesDTO) {
        log.info("Creating new Role: {}", rolesDTO.getRoleName());
        RolesDTO createdRole = rolesService.createRole(rolesDTO);
        return ResponseEntity.ok(createdRole);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RolesDTO> updateRole(@PathVariable Long id, @RequestBody RolesDTO rolesDTO) {

        log.info("Updating role with id: {}", id);
        RolesDTO updatedRole = rolesService.updateRole(id, rolesDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RolesDTO> getRoleById(@PathVariable Long id) {

        log.info("Getting the role with id: {}", id);
        RolesDTO gettingRole = rolesService.getRoleById(id);
        return ResponseEntity.ok(gettingRole);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<RolesDTO>> getAllRoles() {
        log.info("Getting all the roles");
        List<RolesDTO> allRoles = rolesService.getAllRoles();
        return ResponseEntity.ok(allRoles);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {

        log.info("Deleting role with id: {}", id);
        rolesService.deleteRole(id);
        return ResponseEntity.ok("Role with id: " + id + " deleted successfully");
    }
}
