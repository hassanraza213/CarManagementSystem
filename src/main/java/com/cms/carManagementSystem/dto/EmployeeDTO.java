package com.cms.carManagementSystem.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDTO {

	private Long employeeId;

	@JsonProperty("department")
	private DepartmentDTO departmentDTO;
	
	private Long departmentId;

	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "maximum 100 characters allowed")
	private String name;

	@NotNull(message = "Employee rank is required")
	private BigDecimal employeeRank;

	@Size(max = 100, message = "maximum 100 characters allowed")
	private String description;
}
