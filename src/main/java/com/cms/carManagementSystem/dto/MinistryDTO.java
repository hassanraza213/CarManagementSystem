package com.cms.carManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MinistryDTO {

    private Long ministryId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can't exceed 100 Characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 200, message = "address can't exceed 200 Characters")
    private String address;

    @Size(max = 500, message = "description can't exceed 500 Characters")
    private String description;
}
