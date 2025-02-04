package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MaintenanceDTO {

    private Long maintenanceId;
    private Long carId;

    @NotNull(message = "Maintenance Date is required")
    private LocalDate maintenanceDate;

    @NotNull(message = "Maintenance Cost is required")
    private BigDecimal maintenanceCost;

    @NotNull(message = "Maintenance Description is required")
    private String maintenanceDescription;

    @JsonProperty("car")
    private CarDTO carDTO;

    @NotNull(message = "Description is required")
    private String description;
}
