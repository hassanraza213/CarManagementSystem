package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.EmpCarHistoryDTO;
import com.cms.carManagementSystem.service.EmpCarHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeecarhistory")
@Slf4j
public class EmpCarHistoryController {

    @Autowired
    private final EmpCarHistoryService empCarHistoryService;

    public EmpCarHistoryController(EmpCarHistoryService empCarHistoryService) {
        this.empCarHistoryService = empCarHistoryService;
    }

    @PostMapping
    public ResponseEntity<EmpCarHistoryDTO> createEmpCarHistory(@RequestBody EmpCarHistoryDTO empCarHistoryDTO) {
        log.info("Creating employee car history");
        EmpCarHistoryDTO createdHistory = empCarHistoryService.createEmpCarHistory(empCarHistoryDTO);
        return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpCarHistoryDTO> updateEmpCarHistory(@PathVariable Long id, @RequestBody EmpCarHistoryDTO empCarHistoryDTO) {
        log.info("Updating employee car history with ID: {}", id);
        EmpCarHistoryDTO updatedHistory = empCarHistoryService.updateEmpCarHistoryDetails(id, empCarHistoryDTO);
        return ResponseEntity.ok(updatedHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpCarHistoryDTO> getEmpCarHistoryById(@PathVariable Long id) {
        log.info("Fetching employee car history with ID: {}", id);
        EmpCarHistoryDTO history = empCarHistoryService.getEmpCarHistoryDetailsById(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping
    public ResponseEntity<List<EmpCarHistoryDTO>> getAllEmpCarHistories() {
        log.info("Fetching all employee car histories");
        List<EmpCarHistoryDTO> histories = empCarHistoryService.getAllEmpCarHistoryDetails();
        return ResponseEntity.ok(histories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmpCarHistory(@PathVariable Long id) {
        log.info("Deleting employee car history with ID: {}", id);
        empCarHistoryService.deleteEmpCarHistoryDetails(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee car history details not found with Id " + id);
    }
}
