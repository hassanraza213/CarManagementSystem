package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpCarAllocationDTO {
    private Long empCarAllocationId;
    private Long employeeId;
    private Long carId;

    @JsonProperty("car")
    private CarDTO carDTO;

    @JsonProperty("employee")
    private EmployeeDTO employeeDTO;

    @Size(max = 100, message = "maximum 100 characters allowed")
    private String description;
}
