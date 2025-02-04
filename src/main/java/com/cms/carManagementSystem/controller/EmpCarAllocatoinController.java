package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.EmpCarAllocationDTO;
import com.cms.carManagementSystem.service.EmpCarAllocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeecarallocation")
@Slf4j
public class EmpCarAllocatoinController {

    @Autowired
    private final EmpCarAllocationService empCarAllocationService;

    public EmpCarAllocatoinController(EmpCarAllocationService empCarAllocationService) {
        this.empCarAllocationService = empCarAllocationService;
    }

    @PostMapping
    public ResponseEntity<EmpCarAllocationDTO> createEmpCarAllocation(@RequestBody EmpCarAllocationDTO empCarAllocationDTO) {
        log.info("Creating employee car allocation");
        EmpCarAllocationDTO createdAllocation = empCarAllocationService.createEmpCarAllocation(empCarAllocationDTO);
        return new ResponseEntity<>(createdAllocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpCarAllocationDTO> updateEmpCarAllocation(@PathVariable Long id, @RequestBody EmpCarAllocationDTO empCarAllocationDTO) {
        log.info("Updating employee car allocation with ID: {}", id);
        EmpCarAllocationDTO updatedAllocation = empCarAllocationService.updateEmpCarAllocationDetails(id, empCarAllocationDTO);
        return ResponseEntity.ok(updatedAllocation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpCarAllocationDTO> getEmpCarAllocationById(@PathVariable Long id) {
        log.info("Fetching employee car allocation with ID: {}", id);
        EmpCarAllocationDTO allocation = empCarAllocationService.getEmpCarAllocationDetailsById(id);
        return ResponseEntity.ok(allocation);
    }

    @GetMapping
    public ResponseEntity<List<EmpCarAllocationDTO>> getAllEmpCarAllocations() {
        log.info("Fetching all employee car allocations");
        List<EmpCarAllocationDTO> allocations = empCarAllocationService.getAllEmpCarAllocationDetails();
        return ResponseEntity.ok(allocations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmpCarAllocation(@PathVariable Long id) {
        log.info("Deleting employee car allocation with ID: {}", id);
        empCarAllocationService.deleteEmpCarAllocationDetails(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("employee car allocation details not found with Id "+id);
    }
}
