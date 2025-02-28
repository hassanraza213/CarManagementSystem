package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.EmployeeDTO;
import com.cms.carManagementSystem.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createEmployeeDTO = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(createEmployeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
                                                      @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO updateEmployeeDTO = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updateEmployeeDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        try {
            EmployeeDTO employeeByIdDTO = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employeeByIdDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        try {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok("Employee with id " + id + " has been deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id " + id + " is not found");
        }
    }
}
