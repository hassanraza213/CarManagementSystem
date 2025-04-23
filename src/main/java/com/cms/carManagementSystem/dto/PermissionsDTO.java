package com.cms.carManagementSystem.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PermissionsDTO {

    private Long permissionId;

    private String permissionName;

    @Size(max = 500, message = "maximum 500 characters allowed")
    private String description;

}
