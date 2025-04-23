package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.RolesDTO;
import com.cms.carManagementSystem.entity.Roles;
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
public class RolesService {

    @Autowired
    private final RolesRepo rolesRepo;
    private final ModelMapper modelMapper;

    public RolesService(RolesRepo rolesRepo, ModelMapper modelMapper) {
        this.rolesRepo = rolesRepo;
        this.modelMapper = modelMapper;
    }

    public RolesDTO createRole(RolesDTO rolesDTO) {

        Roles rolesEntity = modelMapper.map(rolesDTO, Roles.class);
        Roles savedRoles = rolesRepo.save(rolesEntity);
        RolesDTO rolesDTOResponse = modelMapper.map(savedRoles, RolesDTO.class);
        log.info("Role created with the id: {}", savedRoles.getRoleId());
        return rolesDTOResponse;
    }

    public RolesDTO updateRole(Long id, RolesDTO rolesDTO) {
        Roles existingRole = rolesRepo.findById(id).orElseThrow(() -> {
            log.error("Role not found with id: {}", id);
            return new EntityNotFoundException("Role not found with id: " + id);
        });

        existingRole.setRoleName(rolesDTO.getRoleName());
        existingRole.setDescription(rolesDTO.getDescription());

        Roles savedRoles = rolesRepo.save(existingRole);
        RolesDTO updatedRoles = modelMapper.map(savedRoles, RolesDTO.class);
        log.info("Updated the role with id: {}", savedRoles.getRoleId());
        return updatedRoles;
    }

    public RolesDTO getRoleById(Long id) {

        Roles roles = rolesRepo.findById(id).orElseThrow(() -> {
            log.error("Role not found with id: {}", id);
            return new EntityNotFoundException("Role not found with id: " + id);
        });
        RolesDTO rolesDTO = modelMapper.map(roles, RolesDTO.class);
        log.info("Fetched the role with id: {}", id);
        return rolesDTO;
    }

    public List<RolesDTO> getAllRoles() {
        List<Roles> rolesList = rolesRepo.findAll();
        return rolesList.stream().map(roles -> modelMapper.map(roles, RolesDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteRole(Long id) {

        Roles deleteRole = rolesRepo.findById(id).orElseThrow(() -> {
            log.error("Role not found with id: {}", id);
            return new EntityNotFoundException("Role not found with id: " + id);
        });

        rolesRepo.delete(deleteRole);
        log.info("Role deleted with the id: {}", deleteRole.getRoleId());
    }
}
