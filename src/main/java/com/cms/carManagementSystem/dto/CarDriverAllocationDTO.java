package com.cms.carManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarDriverAllocationDTO {

    private Long carDriverAllocationId;

    private Long carId;

    private Long driverId;

    @JsonProperty("car")
    private CarDTO carDTO;

    @JsonProperty("driver")
    private DriverDTO driverDTO;

    @Size(max = 500, message = "Maximum 500 characters allowed")
    private String description;

}
