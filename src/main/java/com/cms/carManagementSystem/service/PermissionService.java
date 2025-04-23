package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.PermissionsDTO;
import com.cms.carManagementSystem.entity.Permissions;
import com.cms.carManagementSystem.repository.PermissionRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionService {

    @Autowired
    private final PermissionRepo permissionRepo;
    private final ModelMapper modelMapper;

    public PermissionService(PermissionRepo permissionRepo, ModelMapper modelMapper) {
        this.permissionRepo = permissionRepo;
        this.modelMapper = modelMapper;
    }

    public PermissionsDTO createPermissions(PermissionsDTO permissionsDTO) {
        Permissions permissionsEntity = modelMapper.map(permissionsDTO, Permissions.class);
        Permissions savedPermissions = permissionRepo.save(permissionsEntity);
        PermissionsDTO permissionsDTOResponse = modelMapper.map(savedPermissions, PermissionsDTO.class);
        log.info("permission created with id: {}", savedPermissions.getPermissionId());
        return permissionsDTOResponse;
    }

    public PermissionsDTO updatePermission(Long id, PermissionsDTO permissionsDTO) {
        Permissions permissions = permissionRepo.findById(id).orElseThrow(() -> {
            log.error("Permission not found with id: {}", id);
            return new EntityNotFoundException("Permission not found with id: {}" + id);
        });

        permissions.setPermissionName(permissionsDTO.getPermissionName());
        permissions.setDescription(permissionsDTO.getDescription());

        Permissions savedPermission = permissionRepo.save(permissions);
        PermissionsDTO updatedPermissions = modelMapper.map(savedPermission, PermissionsDTO.class);
        log.info("Updated the permission with the id: {}", savedPermission.getPermissionId());
        return updatedPermissions;
    }

    public PermissionsDTO getPermissionById(Long id) {

        Permissions permissions = permissionRepo.findById(id).orElseThrow(() -> {
            log.error("Permission not found with id: {}", id);
            return new EntityNotFoundException("Permission not found with id: " + id);
        });
        PermissionsDTO fetchPermissionDTO = modelMapper.map(permissions, PermissionsDTO.class);
        log.info("Permission fetched with the id: {}", id);
        return fetchPermissionDTO;
    }

    public List<PermissionsDTO> getAllPermission() {

        List<Permissions> permissions = permissionRepo.findAll();
        return permissions.stream().map(permission -> modelMapper.map(permission, PermissionsDTO.class))
                .collect(Collectors.toList());
    }

    public void deletePermission(Long id) {
        Permissions permissions = permissionRepo.findById(id).orElseThrow(() -> {
            log.error("Permission not found with id: {}", id);
            return new EntityNotFoundException("permission not found with id: " + id);
        });
        permissionRepo.delete(permissions);
        log.info("permission deleted successfully with id: {}", permissions.getPermissionId());
    }
}
