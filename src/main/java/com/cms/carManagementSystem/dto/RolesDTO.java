package com.cms.carManagementSystem.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolesDTO {
    private Long roleId;

    private String roleName;

    @Size(max = 500, message = "maximum 500 characters allowed")
    private String description;
}