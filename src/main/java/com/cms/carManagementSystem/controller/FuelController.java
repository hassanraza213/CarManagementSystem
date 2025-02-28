package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.FuelDTO;
import com.cms.carManagementSystem.service.FuelService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuels")
@Slf4j
public class FuelController {

    @Autowired
    private final FuelService fuelService;

    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @PostMapping
    public ResponseEntity<FuelDTO> createFuel(@Valid @RequestBody FuelDTO fuelDTO) {
        log.info("Adding fuel details {}", fuelDTO);
        FuelDTO createFuel = fuelService.createFuel(fuelDTO);
        log.info("Fuel details added successfully {}", createFuel);
        return new ResponseEntity<>(createFuel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelDTO> updateFuelDetials(@PathVariable Long id, @Valid @RequestBody FuelDTO fuelDTO) {
        log.info("Fetching fuel details with Id: {}", id);
        try {
            FuelDTO updateFuelDetail = fuelService.updateFuelDetails(id, fuelDTO);
            log.info("Fuel details updated successfully {}", updateFuelDetail);
            return ResponseEntity.ok(updateFuelDetail);
        } catch (EntityNotFoundException e) {
            log.error("Fuel details not found with Id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelDTO> getFuelDetailsByID(@PathVariable Long id) {
        try {
            FuelDTO getFuelDetails = fuelService.getFuelDetailsById(id);
            log.info("Fuel details fetched successfully {}", getFuelDetails);
            return ResponseEntity.ok(getFuelDetails);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<FuelDTO> getAllFuelDetails() {
        log.info("Fetching all fuel details");
        return fuelService.getAllFuelDetails();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFuelDetailsById(@PathVariable Long id) {
        log.info("Attempting to delete fuel details with Id: {}", id);
        try {
            fuelService.deleteFuelDetailsById(id);
            log.info("Fuel details with Id {} deleted successfully ", id);
            return ResponseEntity.ok("Fuel details with Id " + id + " deleted successfully ");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fuel details not found with Id " + id);
        }
    }
}
