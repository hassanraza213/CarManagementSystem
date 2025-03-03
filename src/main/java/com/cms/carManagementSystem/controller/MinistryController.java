package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.service.MinistryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ministries")
@Slf4j
public class MinistryController {

    @Autowired
    private final MinistryService ministryService;

    public MinistryController(MinistryService ministryService) {
        this.ministryService = ministryService;
    }

    @PostMapping
    public ResponseEntity<MinistryDTO> createMinistry(@Valid @RequestBody MinistryDTO ministryDTO) {
        log.info("Creating new ministry: {}", ministryDTO);
        MinistryDTO createMinistry = ministryService.createMinistry(ministryDTO);
        log.info("Ministry created successfully: {}", createMinistry);
        return new ResponseEntity<>(createMinistry, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MinistryDTO> updateMinistry(@PathVariable Long id, @Valid @RequestBody MinistryDTO ministryDTO) {
        log.info("Updating ministry with ID: {} and data: {}", id, ministryDTO);
        try {
            MinistryDTO updateMinistry = ministryService.updateMinistry(id, ministryDTO);
            log.info("Ministry updated successfully: {}", updateMinistry);
            return ResponseEntity.ok(updateMinistry);
        } catch (EntityNotFoundException e) {
            log.error("Ministry not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MinistryDTO> getMinistryById(@PathVariable Long id) {
        log.info("Fetching ministry with ID: {}", id);
        try {
            MinistryDTO ministryDTO = ministryService.getMinistryById(id);
            log.info("Ministry found: {}", ministryDTO);
            return ResponseEntity.ok(ministryDTO);
        } catch (EntityNotFoundException e) {
            log.error("Ministry not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<MinistryDTO> getAllMinistries() {
        log.info("Fetching all ministries");
        List<MinistryDTO> ministries = ministryService.getAllMinistries();
        log.info("Retrieved {} ministries", ministries.size());
        return ministries;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMinistry(@PathVariable Long id) {
        log.info("Attempting to delete ministry with ID: {}", id);
        try {
            ministryService.deleteMinistry(id);
            log.info("Ministry with ID {} deleted successfully", id);
            return ResponseEntity.ok("Ministry with ID " + id + " has been deleted.");
        } catch (EntityNotFoundException e) {
            log.error("Failed to delete ministry with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ministry with ID " + id + " not found.");
        }
    }
}
