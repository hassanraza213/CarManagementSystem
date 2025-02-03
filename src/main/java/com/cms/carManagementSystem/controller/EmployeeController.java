package com.cms.carManagementSystem.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.carManagementSystem.dto.EmployeeDTO;
import com.cms.carManagementSystem.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Adding employee {}", employeeDTO);
        EmployeeDTO createEmployeeDTO = employeeService.createEmployee(employeeDTO);
        log.info("Employee added successfully {}", createEmployeeDTO);
        return new ResponseEntity<>(createEmployeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
                                                      @Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Getting employee with Id: {}", id);
        try {
            EmployeeDTO updateEmployeeDTO = employeeService.updateEmployee(id, employeeDTO);
            log.info("Employee with id {} details updated successfully", updateEmployeeDTO);
            return ResponseEntity.ok(updateEmployeeDTO);
        } catch (EntityNotFoundException e) {
            log.error("Employee not found with Id: {]", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with Id: {}", id);
        try {
            EmployeeDTO employeeByIdDTO = employeeService.getEmployeeById(id);
            log.info("Employee found with Id: {}", id);
            return ResponseEntity.ok(employeeByIdDTO);
        } catch (EntityNotFoundException e) {
            log.error("Employee not found with Id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        log.info("Attempting to delete employee with Id: {}", id);
        try {
            employeeService.deleteEmployeeById(id);
            log.info("Employee with Id {} deleted successfully", id);
            return ResponseEntity.ok("Employee with id" + id + " has been deleted");
        } catch (EntityNotFoundException e) {
            log.error("Employee not found with Id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id " + id + " is not found");
        }
    }
}
