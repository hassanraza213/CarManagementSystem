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

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.service.DepartmentService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
@Slf4j
public class DepartmentController {

    @Autowired
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        log.info("Creating department {}", departmentDTO);
        DepartmentDTO createDepartment = departmentService.createDepartment(departmentDTO);
        log.info("Department created successfully {}", createDepartment);
        return new ResponseEntity<>(createDepartment, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        log.info("Fetching department with Id: {}", id);
        try {
            DepartmentDTO updateDepartment = departmentService.updateDepartment(id, departmentDTO);
            log.info("Department updated successfully {}", updateDepartment);
            return ResponseEntity.ok(updateDepartment);
        } catch (EntityNotFoundException e) {
            log.error("Department not found with Id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        log.info("Fetching department with Id : {}", id);
        try {
            DepartmentDTO departmentByID = departmentService.getDepartmentById(id);
            log.info("Department found {}", departmentByID);
            return ResponseEntity.ok(departmentByID);
        } catch (EntityNotFoundException e) {
            log.error("Department not found with Id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        log.info("Fetching all departments");
        return departmentService.getAllDepartments();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeparmentById(@PathVariable Long id) {
        log.info("Attempting to delete department with Id: {}", id);
        try {
            departmentService.deleteDepartment(id);
            log.info("Department with Id {} deleted successfully", id);
            return ResponseEntity.ok("Department with id " + id + " has been deleted");
        } catch (EntityNotFoundException e) {
            log.error("Department not found with Id: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with id " + id + " is not found");
        }
    }
}
