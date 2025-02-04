package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.CarDriverAllocationDTO;
import com.cms.carManagementSystem.service.CarDriverAllocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cardriverallocation")
@Slf4j
public class CarDriverAllocationController {

    @Autowired
    private final CarDriverAllocationService carDriverAllocationService;

    public CarDriverAllocationController(CarDriverAllocationService carDriverAllocationService) {
        this.carDriverAllocationService = carDriverAllocationService;
    }

    @PostMapping
    public ResponseEntity<CarDriverAllocationDTO> createCarDriverAllocation(@RequestBody CarDriverAllocationDTO carDriverAllocationDTO) {
        log.info("Creating car driver allocation");
        CarDriverAllocationDTO createdAllocation = carDriverAllocationService.createCarDriverAllocation(carDriverAllocationDTO);
        return new ResponseEntity<>(createdAllocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDriverAllocationDTO> updateCarDriverAllocation(@PathVariable Long id, @RequestBody CarDriverAllocationDTO carDriverAllocationDTO) {
        log.info("Updating car driver allocation with ID: {}", id);
        CarDriverAllocationDTO updatedAllocation = carDriverAllocationService.updateCarDriverAllocationDetails(id, carDriverAllocationDTO);
        return ResponseEntity.ok(updatedAllocation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDriverAllocationDTO> getCarDriverAllocationById(@PathVariable Long id) {
        log.info("Fetching car driver allocation with ID: {}", id);
        CarDriverAllocationDTO allocation = carDriverAllocationService.getCarDriverAllocationDetailsById(id);
        return ResponseEntity.ok(allocation);
    }

    @GetMapping
    public ResponseEntity<List<CarDriverAllocationDTO>> getAllCarDriverAllocations() {
        log.info("Fetching all car driver allocations");
        List<CarDriverAllocationDTO> allocations = carDriverAllocationService.getAllCarDriverAllocationDetails();
        return ResponseEntity.ok(allocations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarDriverAllocation(@PathVariable Long id) {
        log.info("Deleting car driver allocation with ID: {}", id);
        carDriverAllocationService.deleteCarDriverAllocationDetails(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car driver allocation details not found with Id " + id);
    }
}
