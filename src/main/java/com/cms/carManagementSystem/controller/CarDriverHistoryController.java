package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.CarDriverHistoryDTO;
import com.cms.carManagementSystem.service.CarDriverHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cardriverhistory")
@Slf4j
public class CarDriverHistoryController {

    @Autowired
    private final CarDriverHistoryService carDriverHistoryService;

    public CarDriverHistoryController(CarDriverHistoryService carDriverHistoryService) {
        this.carDriverHistoryService = carDriverHistoryService;
    }

    @PostMapping
    public ResponseEntity<CarDriverHistoryDTO> createCarDriverHistory(@RequestBody CarDriverHistoryDTO carDriverHistoryDTO) {
        log.info("Creating car driver history");
        CarDriverHistoryDTO createdHistory = carDriverHistoryService.createCarDriverHistory(carDriverHistoryDTO);
        return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDriverHistoryDTO> updateCarDriverHistory(@PathVariable Long id, @RequestBody CarDriverHistoryDTO carDriverHistoryDTO) {
        log.info("Updating car driver history with ID: {}", id);
        CarDriverHistoryDTO updatedHistory = carDriverHistoryService.updateCarDriverHistory(id, carDriverHistoryDTO);
        return ResponseEntity.ok(updatedHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDriverHistoryDTO> getCarDriverHistoryById(@PathVariable Long id) {
        log.info("Fetching car driver history with ID: {}", id);
        CarDriverHistoryDTO history = carDriverHistoryService.getCarDriverHistoryById(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping
    public ResponseEntity<List<CarDriverHistoryDTO>> getAllCarDriverHistories() {
        log.info("Fetching all car driver histories");
        List<CarDriverHistoryDTO> histories = carDriverHistoryService.getAllCarDriverHistoryDetails();
        return ResponseEntity.ok(histories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarDriverHistory(@PathVariable Long id) {
        log.info("Deleting car driver history with ID: {}", id);
        carDriverHistoryService.deleteCarDriverHistory(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car driver history details not found with Id " + id);
    }
}
