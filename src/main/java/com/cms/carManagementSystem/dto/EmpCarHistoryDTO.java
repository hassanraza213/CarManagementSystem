package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpCarHistoryDTO {
    private Long empCarHistoryId;
    private Long employeeId;
    private Long carId;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonProperty("car")
    private CarDTO carDTO;

    @JsonProperty("employee")
    private EmployeeDTO employeeDTO;

    @Size(max = 500, message = "maximum 500 characters allowed")
    private String description;
}
