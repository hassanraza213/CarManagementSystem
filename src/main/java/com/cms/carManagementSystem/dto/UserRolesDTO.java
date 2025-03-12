package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRolesDTO {
    private Long userRoleId;

    private Long userId;
    private Long roleId;

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("role")
    private RolesDTO roleDTO;

    @Size(max = 500, message = "maximum 500 characters allowed")
    private String description;
}