package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDTO {

    private Long budgetId;
    private Long departmentId;

    @NotNull(message = "New Car Budget is required")
    private BigDecimal newCarBudget;

    @NotNull(message = "Maintenance Budget is required")
    private BigDecimal maintenanceBudget;

    @JsonProperty("department")
    private DepartmentDTO departmentDTO;

    @NotNull(message = "Description is required")
    private String description;
}
