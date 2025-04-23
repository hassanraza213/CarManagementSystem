package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.AssignPermissionsToRolesDTO;
import com.cms.carManagementSystem.dto.PermissionsDTO;
import com.cms.carManagementSystem.dto.RolesDTO;
import com.cms.carManagementSystem.entity.AssignPermissionsToRoles;
import com.cms.carManagementSystem.entity.Permissions;
import com.cms.carManagementSystem.entity.Roles;
import com.cms.carManagementSystem.repository.AssignPermissionsToRolesRepo;
import com.cms.carManagementSystem.repository.PermissionRepo;
import com.cms.carManagementSystem.repository.RolesRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AssignPermissionsToRolesService {

    @Autowired
    private final AssignPermissionsToRolesRepo permissionsToRolesRepo;
    private final RolesRepo rolesRepo;
    private final PermissionRepo permissionRepo;
    private final ModelMapper modelMapper;

    public AssignPermissionsToRolesService(AssignPermissionsToRolesRepo permissionsToRolesRepo, RolesRepo rolesRepo, PermissionRepo permissionRepo, ModelMapper modelMapper) {
        this.permissionsToRolesRepo = permissionsToRolesRepo;
        this.rolesRepo = rolesRepo;
        this.permissionRepo = permissionRepo;
        this.modelMapper = modelMapper;
    }

    public AssignPermissionsToRolesDTO createPermissionsToRoles(AssignPermissionsToRolesDTO assignPermissionToRolesDTO) {
        Roles roles = rolesRepo.findById(assignPermissionToRolesDTO.getRoleId()).orElseThrow(() -> {
            log.error("Role not found with id: {}", assignPermissionToRolesDTO.getRoleId());
            return new EntityNotFoundException("Role not found with id: " + assignPermissionToRolesDTO.getRoleId());
        });

        Permissions permissions = permissionRepo.findById(assignPermissionToRolesDTO.getPermissionId()).orElseThrow(() -> {
            log.error("permission not found with id: {}", assignPermissionToRolesDTO.getPermissionId());
            return new EntityNotFoundException("permission not found with id: " + assignPermissionToRolesDTO.getPermissionId());
        });

        AssignPermissionsToRoles assignPermissionsToRoles = modelMapper.map(assignPermissionToRolesDTO, AssignPermissionsToRoles.class);
        assignPermissionsToRoles.setRoles(roles);
        assignPermissionsToRoles.setPermissions(permissions);

        AssignPermissionsToRoles savedPermissionsToRoles = permissionsToRolesRepo.save(assignPermissionsToRoles);
        AssignPermissionsToRolesDTO permissionToRolesDTO = modelMapper.map(savedPermissionsToRoles, AssignPermissionsToRolesDTO.class);
        permissionToRolesDTO.setRoleId(roles.getRoleId());
        permissionToRolesDTO.setPermissionId(permissions.getPermissionId());

        RolesDTO rolesDTO = modelMapper.map(roles, RolesDTO.class);
        permissionToRolesDTO.setRoleDTO(rolesDTO);

        PermissionsDTO permissionsDTO = modelMapper.map(permissions, PermissionsDTO.class);
        permissionToRolesDTO.setPermissionDTO(permissionsDTO);

        log.info("permission assigned to the role with id: {}", savedPermissionsToRoles.getAssignId());
        return permissionToRolesDTO;
    }

    public AssignPermissionsToRolesDTO updatePermissionsToRoles(Long id, AssignPermissionsToRolesDTO assignPermissionToRolesDTO) {
        AssignPermissionsToRoles existingPermissionsToRoles = permissionsToRolesRepo.findById(id).orElseThrow(() -> {
            log.error("Assignment not found with id: {}", id);
            return new EntityNotFoundException("Assignment not found with id: " + id);
        });

        Roles roles = rolesRepo.findById(assignPermissionToRolesDTO.getRoleId()).orElseThrow(() -> {
            log.error("Role not found with id: {}", assignPermissionToRolesDTO.getRoleId());
            return new EntityNotFoundException("Role not found with id: " + assignPermissionToRolesDTO.getRoleId());
        });

        Permissions permissions = permissionRepo.findById(assignPermissionToRolesDTO.getPermissionId()).orElseThrow(() -> {
            log.error("permission not found with id: {}", assignPermissionToRolesDTO.getPermissionId());
            return new EntityNotFoundException("permission not found with id: " + assignPermissionToRolesDTO.getPermissionId());
        });

        existingPermissionsToRoles.setDescription(assignPermissionToRolesDTO.getDescription());
        existingPermissionsToRoles.setRoles(roles);
        existingPermissionsToRoles.setPermissions(permissions);
        AssignPermissionsToRoles savedPermissionsToRoles = permissionsToRolesRepo.save(existingPermissionsToRoles);

        AssignPermissionsToRolesDTO updatedPermissionToRolesDTO = modelMapper.map(savedPermissionsToRoles, AssignPermissionsToRolesDTO.class);
        updatedPermissionToRolesDTO.setRoleId(roles.getRoleId());
        updatedPermissionToRolesDTO.setPermissionId(permissions.getPermissionId());

        RolesDTO rolesDTO = modelMapper.map(roles, RolesDTO.class);
        updatedPermissionToRolesDTO.setRoleDTO(rolesDTO);

        PermissionsDTO permissionsDTO = modelMapper.map(permissions, PermissionsDTO.class);
        updatedPermissionToRolesDTO.setPermissionDTO(permissionsDTO);

        log.info("permission to role is updated with the id: {}", savedPermissionsToRoles.getAssignId());
        return updatedPermissionToRolesDTO;
    }

    public AssignPermissionsToRolesDTO getPermissionsToRolesById(Long id) {
        AssignPermissionsToRoles permissionsToRoles = permissionsToRolesRepo.findById(id).orElseThrow(() -> {
            log.error("Assignment not found with id: {}", id);
            return new EntityNotFoundException("Assignment not found with id: " + id);
        });

        AssignPermissionsToRolesDTO getPermissionToRolesDTO = modelMapper.map(permissionsToRoles, AssignPermissionsToRolesDTO.class);
        getPermissionToRolesDTO.setRoleId(permissionsToRoles.getRoles().getRoleId());
        getPermissionToRolesDTO.setPermissionId(permissionsToRoles.getPermissions().getPermissionId());

        RolesDTO roleDTO = modelMapper.map(permissionsToRoles.getRoles(), RolesDTO.class);
        getPermissionToRolesDTO.setRoleDTO(roleDTO);

        PermissionsDTO permissionDTO = modelMapper.map(permissionsToRoles.getPermissions(), PermissionsDTO.class);
        getPermissionToRolesDTO.setPermissionDTO(permissionDTO);

        log.info("Fetched permission to role assignment with id: {}", id);
        return getPermissionToRolesDTO;
    }

    public List<AssignPermissionsToRolesDTO> getAllPermissionToRoles() {

        List<AssignPermissionsToRoles> permissionsToRolesList = permissionsToRolesRepo.findAll();
        return permissionsToRolesList.stream().map(permissionsToRoles -> {
            AssignPermissionsToRolesDTO permissionToRolesDTO = modelMapper.map(permissionsToRoles, AssignPermissionsToRolesDTO.class);

            permissionToRolesDTO.setRoleId(permissionsToRoles.getRoles().getRoleId());
            permissionToRolesDTO.setPermissionId(permissionsToRoles.getPermissions().getPermissionId());

            RolesDTO rolesDTO = modelMapper.map(permissionsToRoles.getRoles(), RolesDTO.class);
            permissionToRolesDTO.setRoleDTO(rolesDTO);

            PermissionsDTO permissionsDTO = modelMapper.map(permissionsToRoles.getPermissions(), PermissionsDTO.class);
            permissionToRolesDTO.setPermissionDTO(permissionsDTO);

            return permissionToRolesDTO;
        }).collect(Collectors.toList());
    }

    public void deletePermissionsToRoles(Long id) {
        AssignPermissionsToRoles permissionsToRoles = permissionsToRolesRepo.findById(id).orElseThrow(() -> {
            log.error("Permissions to role not found with this id: {}", id);
            return new EntityNotFoundException("Permission to role not found with id: " + id);
        });

        permissionsToRolesRepo.delete(permissionsToRoles);
        log.info("Permission to role deleted successfully with id: {}", id);
    }
}
