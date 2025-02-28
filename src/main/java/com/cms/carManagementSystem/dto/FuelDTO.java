package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FuelDTO {
    private Long fuelId;
    private Long carId;

    @JsonProperty("car")
    private CarDTO carDTO;

    @NotBlank(message = "fuelType is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String fuelType;

    @NotNull(message = "fuelQuantity is required")
    @DecimalMin(value = "0.1", message = "fuelQuantity must be greater than zero")
    private BigDecimal fuelQuantity;

    @NotNull(message = "date is required")
    @PastOrPresent(message = "date cannot be in the future")
    private LocalDate date;

    @NotBlank(message = "description is required")
    @Size(max = 100, message = "maximum 100 characters allowed")
    private String description;
}
