package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AssignPermissionsToRolesDTO {

    private Long assignId;

    private Long roleId;
    private Long permissionId;

    @JsonProperty("role")
    private RolesDTO roleDTO;

    @JsonProperty("permission")
    private PermissionsDTO permissionDTO;

    @Size(max = 500, message = "maximum 500 characters allowed")
    private String description;
}
