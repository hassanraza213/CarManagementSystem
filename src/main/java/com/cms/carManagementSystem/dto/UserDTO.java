package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can't exceed 100 Characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "password can't exceed 50 Characters")
    @JsonIgnore
    private String password;

    @Size(max = 500, message = "description can't exceed 500 Characters")
    private String description;
}
