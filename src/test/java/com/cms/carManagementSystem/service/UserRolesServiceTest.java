package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.RolesDTO;
import com.cms.carManagementSystem.dto.UserDTO;
import com.cms.carManagementSystem.dto.UserRolesDTO;
import com.cms.carManagementSystem.entity.AssignPermissionsToRoles;
import com.cms.carManagementSystem.entity.Roles;
import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.entity.UserRoles;
import com.cms.carManagementSystem.repository.RolesRepo;
import com.cms.carManagementSystem.repository.UserRepo;
import com.cms.carManagementSystem.repository.UserRolesRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRolesServiceTest {

    @Mock
    private UserRolesRepo userRolesRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private RolesRepo rolesRepo;

    @Mock
    private AssignPermissionsToRoles permissionsToRoles;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserRolesService userRolesService;

    private User user;
    private Roles roles;
    private UserRoles userRoles;
    private UserRolesDTO userRolesDTO;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setUserId(1L);
        user.setUserName("test.user");
        user.setPassword("hashedPassword");
        user.setDescription("Test user");

        roles = new Roles();
        roles.setRoleId(1L);
        roles.setRoleName("ROLE_TEST");

        userRoles = new UserRoles();
        userRoles.setUserRoleId(1L);
        userRoles.setUser(user);
        userRoles.setRoles(roles);
        userRoles.setDescription("Test User Role");

        userRolesDTO = new UserRolesDTO();
        userRolesDTO.setUserId(1L);
        userRolesDTO.setRoleId(1L);
        userRolesDTO.setDescription("Test User Role");
    }

    @Test
    void createUserRolesTestSuccess(){
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(rolesRepo.findById(1L)).thenReturn(Optional.of(roles));
        when(modelMapper.map(userRolesDTO, UserRoles.class)).thenReturn(userRoles);
        when(userRolesRepo.save(userRoles)).thenReturn(userRoles);
        when(modelMapper.map(userRoles, UserRolesDTO.class)).thenReturn(userRolesDTO);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(new UserDTO());
        when(modelMapper.map(roles, RolesDTO.class)).thenReturn(new RolesDTO());

        UserRolesDTO result = userRolesService.createUserRoles(userRolesDTO);
        assertNotNull(result);
        assertEquals(userRolesDTO.getDescription(), result.getDescription());
        verify(userRolesRepo,times(1)).save(userRoles);
    }

    @Test
    void createUserRolesTestFailure(){
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                userRolesService.createUserRoles(userRolesDTO));
        assertEquals("user not found with id : 1", exception.getMessage());
        verify(userRolesRepo, never()).save(any());
    }

    @Test
    void getUserRoleByIdTestSuccess(){
        List<UserRoles> userRolesList = Arrays.asList(userRoles);
        when(userRolesRepo.findByUser_UserId(1L)).thenReturn(userRolesList);

        List<String> roles = userRolesService.getRolesByUserId(1L);
        assertEquals(1, roles.size());
        assertEquals("ROLE_TEST", roles.get(0));
        verify(userRolesRepo, times(1)).findByUser_UserId(1L);
    }

    @Test
    void getUserRoleByIdTestFailure(){
        when(userRolesRepo.findByUser_UserId(1L)).thenReturn(Arrays.asList());

        List<String> roles = userRolesService.getRolesByUserId(1L);
        assertTrue(roles.isEmpty());
        verify(userRolesRepo, times(1)).findByUser_UserId(1L);
    }

}
