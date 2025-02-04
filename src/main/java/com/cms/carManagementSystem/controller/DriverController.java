package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.DriverDTO;
import com.cms.carManagementSystem.service.DriverService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@Slf4j
public class DriverController {

    @Autowired
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverDTO> createDriver(@Valid @RequestBody DriverDTO driverDTO) {
        log.info("Adding new Driver: {}", driverDTO);
        DriverDTO createDriver = driverService.createDriver(driverDTO);
        log.info("Driver added successfully: {}", createDriver);
        return new ResponseEntity<>(createDriver, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverDTO driverDTO) {
        log.info("Updating driver with Id: {}", id, driverDTO);
        try {
            DriverDTO updateDriver = driverService.updateDriver(id, driverDTO);
            log.info("Driver updated successfully {}", updateDriver);
            return ResponseEntity.ok(updateDriver);
        } catch (EntityNotFoundException e) {
            log.error("Driver not found with Id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> GetDriverById(@PathVariable Long id) {
        log.info("Fetching driver with Id: {}", id);
        try {
            DriverDTO getDriver = driverService.getDriverById(id);
            log.info("Driver found with Id: {}", id);
            return ResponseEntity.ok(getDriver);
        } catch (EntityNotFoundException e) {
            log.error("Driver not found with Id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<DriverDTO> getAllDrivers() {
        log.info("Fetching the list of drivers");
        return driverService.getAllDrivers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriverById(@PathVariable Long id) {
        log.info("Attempting to delete driver with Id: {}", id);
        try {
            driverService.deleteDriverById(id);
            log.info("Driver with Id {} deleted successfully", id);
            return ResponseEntity.ok("Driver with Id " + id + " has been deleted");
        } catch (EntityNotFoundException e) {
            log.info("Driver not found with Id: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
