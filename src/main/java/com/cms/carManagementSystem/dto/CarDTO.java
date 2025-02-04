package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarDTO {
    private Long carId;
    private Long departmentId;

    @JsonProperty("department")
    private DepartmentDTO departmentDTO;

    @NotNull(message = "carModel is required")
    @Min(value = 1, message = "carModel must be a positive number")
    private int carModel;

    @NotBlank(message = "carMake is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String carMake;

    @NotBlank(message = "carCondition is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String carCondition;

    @NotBlank(message = "carStatus is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String carStatus;

    @NotBlank(message = "description is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String description;

}
