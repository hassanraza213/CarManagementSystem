package com.cms.carManagementSystem.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cms.carManagementSystem.dto.MaintenanceDTO;
import com.cms.carManagementSystem.service.MaintenanceService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/maintenances")
@Slf4j
public class MaintenanceController {

    @Autowired
    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceDTO> createMaintenance(@Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        log.info("Creating new maintenance entry");
        MaintenanceDTO createMaintenanceDTO = maintenanceService.createMaintenance(maintenanceDTO);
        return new ResponseEntity<>(createMaintenanceDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> updateMaintenance(@PathVariable Long id, @Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        log.info("Updating maintenance entry with id: {}", id);
        try {
            MaintenanceDTO updateMaintenanceDTO = maintenanceService.updateMaintenance(id, maintenanceDTO);
            return ResponseEntity.ok(updateMaintenanceDTO);
        } catch (EntityNotFoundException e) {
            log.error("Maintenance not found with id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable Long id) {
        log.info("Fetching maintenance entry with id: {}", id);
        try {
            MaintenanceDTO maintenanceByIdDTO = maintenanceService.getMaintenanceById(id);
            return ResponseEntity.ok(maintenanceByIdDTO);
        } catch (EntityNotFoundException e) {
            log.error("Maintenance not found with id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<MaintenanceDTO> getAllMaintenances() {
        log.info("Fetching all maintenance entries");
        return maintenanceService.getAllMaintenances();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaintenanceById(@PathVariable Long id) {
        log.info("Deleting maintenance entry with id: {}", id);
        try {
            maintenanceService.deleteMaintenanceById(id);
            return ResponseEntity.ok("Maintenance entry with id " + id + " has been deleted");
        } catch (EntityNotFoundException e) {
            log.error("Maintenance not found with id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance entry with id " + id + " is not found");
        }
    }
}
