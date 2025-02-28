package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createDepartment = departmentService.createDepartment(departmentDTO);
        return new ResponseEntity<>(createDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id,
                                                          @Valid @RequestBody DepartmentDTO departmentDTO) {
        try {
            DepartmentDTO updateDepartment = departmentService.updateDepartment(id, departmentDTO);
            return ResponseEntity.ok(updateDepartment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        try {
            DepartmentDTO departmentByID = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(departmentByID);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeparmentById(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("Department with id " + id + " has been deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with id " + id + " is not found");
        }
    }
}
