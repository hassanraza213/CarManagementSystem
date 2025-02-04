package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DriverDTO {

    private Long driverId;
    private Long departmentId;

    @JsonProperty("department")
    private DepartmentDTO departmentDTO;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String driverName;

    @NotBlank(message = "licenseNumber is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String licenseNumber;

    @NotBlank(message = "availability is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String availability;

    @NotBlank(message = "description is required")
    @Size(max = 500, message = "maximum 500 characters allowed")
    private String description;
}
