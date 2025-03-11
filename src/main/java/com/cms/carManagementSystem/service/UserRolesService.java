package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.RolesDTO;
import com.cms.carManagementSystem.dto.UserDTO;
import com.cms.carManagementSystem.dto.UserRolesDTO;
import com.cms.carManagementSystem.entity.Roles;
import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.entity.UserRoles;
import com.cms.carManagementSystem.repository.RolesRepo;
import com.cms.carManagementSystem.repository.UserRepo;
import com.cms.carManagementSystem.repository.UserRolesRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRolesService {

    @Autowired
    private final UserRolesRepo userRolesRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;

    public UserRolesService(UserRolesRepo userRolesRepo, ModelMapper modelMapper, UserRepo userRepo, RolesRepo rolesRepo) {
        this.userRolesRepo = userRolesRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
    }

    public UserRolesDTO createUserRoles(UserRolesDTO userRolesDTO) {

        User user = userRepo.findById(userRolesDTO.getUserId()).orElseThrow(() -> {
            log.error("User not found with id : {}", userRolesDTO.getUserId());
            return new EntityNotFoundException("user not found with id : " + userRolesDTO.getUserId());
        });

        Roles roles = rolesRepo.findById(userRolesDTO.getRoleId()).orElseThrow(() -> {
            log.error("Role not found with id : {}", userRolesDTO.getRoleId());
            return new EntityNotFoundException("Role not found with id : " + userRolesDTO.getRoleId());
        });

        UserRoles userRolesEntity = modelMapper.map(userRolesDTO, UserRoles.class);
        userRolesEntity.setUser(user);
        userRolesEntity.setRoles(roles);

        UserRoles savedUserRoles = userRolesRepo.save(userRolesEntity);
        UserRolesDTO userRolesDTOResponse = modelMapper.map(savedUserRoles, UserRolesDTO.class);
        userRolesDTOResponse.setUserId(user.getUserId());
        userRolesDTOResponse.setRoleId(roles.getRoleId());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userRolesDTOResponse.setUserDTO(userDTO);

        RolesDTO rolesDTO = modelMapper.map(roles, RolesDTO.class);
        userRolesDTOResponse.setRoleDTO(rolesDTO);

        log.info("user role created with id : {} ", savedUserRoles.getUserRoleId());
        return userRolesDTOResponse;
    }

    public UserRolesDTO updateUserRoles(Long id, UserRolesDTO userRolesDTO){

        UserRoles existingUserRoles = userRolesRepo.findById(userRolesDTO.getUserRoleId()).orElseThrow(()->{
            log.error("User role not found with id : {} ", userRolesDTO.getUserRoleId());
            return new EntityNotFoundException("user role not found with id : "+userRolesDTO.getUserRoleId());
        });

        User user = userRepo.findById(userRolesDTO.getUserId()).orElseThrow(()->{
            log.error("user not found with id : {} ", userRolesDTO.getUserId());
            return new EntityNotFoundException("user not found with id : "+userRolesDTO.getUserId());
        });

        Roles roles = rolesRepo.findById(userRolesDTO.getRoleId()).orElseThrow(() -> {
            log.error("Role not found with ID: {}", userRolesDTO.getRoleId());
            return new EntityNotFoundException("Role not found with Id: " + userRolesDTO.getRoleId());
        });

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(userRolesDTO, existingUserRoles);
        existingUserRoles.setUser(user);
        existingUserRoles.setRoles(roles);

        UserRoles savedUserRoles = userRolesRepo.save(existingUserRoles);
        UserRolesDTO updateUserRolesDTO = modelMapper.map(savedUserRoles, UserRolesDTO.class);

        updateUserRolesDTO.setUserId(user.getUserId());
        updateUserRolesDTO.setRoleId(roles.getRoleId());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        updateUserRolesDTO.setUserDTO(userDTO);

        RolesDTO rolesDTO = modelMapper.map(roles, RolesDTO.class);
        updateUserRolesDTO.setRoleDTO(rolesDTO);

        log.info("user role updated with id : {} ", savedUserRoles.getUserRoleId());
        return updateUserRolesDTO;
    }

    public UserRolesDTO getUserRoleById(Long id){

        UserRoles userRoles = userRolesRepo.findById(id).orElseThrow(()->{
            log.error("User role not found with id : {}",id);
            return new EntityNotFoundException("user role not found with id : "+id);
        });

        UserRolesDTO userRolesDTO = modelMapper.map(userRoles, UserRolesDTO.class);
        userRolesDTO.setUserId(userRoles.getUser().getUserId());
        userRolesDTO.setRoleId(userRoles.getRoles().getRoleId());

        UserDTO userDTO = modelMapper.map(userRoles.getUser(), UserDTO.class);
        userRolesDTO.
    }
}
