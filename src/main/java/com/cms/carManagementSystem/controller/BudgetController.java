package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.BudgetDTO;
import com.cms.carManagementSystem.service.BudgetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@Slf4j
public class BudgetController {

    @Autowired
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(@Valid @RequestBody BudgetDTO budgetDTO) {
        log.info("Creating new budget");
        BudgetDTO createBudgetDTO = budgetService.createBudget(budgetDTO);
        return new ResponseEntity<>(createBudgetDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> updateBudget(@PathVariable Long id, @Valid @RequestBody BudgetDTO budgetDTO) {
        log.info("Updating budget with id: {}", id);
        try {
            BudgetDTO updateBudgetDTO = budgetService.updateBudget(id, budgetDTO);
            return ResponseEntity.ok(updateBudgetDTO);
        } catch (EntityNotFoundException e) {
            log.error("Budget not found with id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable Long id) {
        log.info("Fetching budget with id: {}", id);
        try {
            BudgetDTO budgetByIdDTO = budgetService.getBudgetById(id);
            return ResponseEntity.ok(budgetByIdDTO);
        } catch (EntityNotFoundException e) {
            log.error("Budget not found with id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<BudgetDTO> getAllBudgets() {
        log.info("Fetching all budgets");
        return budgetService.getAllBudgets();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudgetById(@PathVariable Long id) {
        log.info("Deleting budget with id: {}", id);
        try {
            budgetService.deleteBudgetById(id);
            return ResponseEntity.ok("Budget with id " + id + " has been deleted");
        } catch (EntityNotFoundException e) {
            log.error("Budget not found with id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Budget with id " + id + " is not found");
        }
    }
}
