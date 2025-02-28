package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CarDriverHistoryDTO {
    private Long carDriverHistoryId;

    private Long carId;
    private Long driverId;
    private LocalDate startDate;

    private LocalDate endDate;

    @JsonProperty("car")
    private CarDTO carDTO;

    @JsonProperty("driver")
    private DriverDTO driverDTO;

    @Size(max = 500, message = "maximum 500 characters allowed")
    private String description;
}
