package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentDTO {

	private Long departmentId;
	
	@JsonProperty("ministry")
	private MinistryDTO ministryDTO;
	
	private Long ministryId;

	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "maximum 100 characters allowed")
	private String name;

	@NotBlank(message = "Description is required")
	@Size(max = 200, message = "maximum 200 characters allowed")
	private String description;
}
