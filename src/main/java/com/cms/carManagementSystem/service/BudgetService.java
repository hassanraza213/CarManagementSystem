package com.cms.carManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.carManagementSystem.dto.BudgetDTO;
import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.entity.Budget;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.repository.BudgetRepo;
import com.cms.carManagementSystem.repository.DepartmentRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepo budgetRepo;

    private ModelMapper modelMapper;

    private DepartmentRepo departmentRepo;

    public BudgetService(BudgetRepo budgetRepo, ModelMapper modelMapper, DepartmentRepo departmentRepo) {
        super();
        this.budgetRepo = budgetRepo;
        this.modelMapper = modelMapper;
        this.departmentRepo = departmentRepo;
    }

    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        Department department = departmentRepo.findById(budgetDTO.getDepartmentId()).orElseThrow(
                () -> new EntityNotFoundException("Department not found with id " + budgetDTO.getDepartmentId()));
        Budget convertDTOToEntity = modelMapper.map(budgetDTO, Budget.class);
        convertDTOToEntity.setDepartment(department);
        Budget savedBudget = budgetRepo.save(convertDTOToEntity);
        BudgetDTO convertEntityToDTO = modelMapper.map(savedBudget, BudgetDTO.class);
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        return convertEntityToDTO;
    }

    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
        Department department = departmentRepo.findById(budgetDTO.getDepartmentId()).orElseThrow(
                () -> new EntityNotFoundException("Department not found with id " + budgetDTO.getDepartmentId()));
        Budget updateBudget = budgetRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found with id " + id));
        modelMapper.map(budgetDTO, updateBudget);
        updateBudget.setDepartment(department);
        Budget updatedBudget = budgetRepo.save(updateBudget);
        BudgetDTO convertEntityToDTO = modelMapper.map(updatedBudget, BudgetDTO.class);
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        return convertEntityToDTO;
    }

    public List<BudgetDTO> getAllBudgets() {
        List<Budget> allBudgets = budgetRepo.findAll();
        return allBudgets.stream().map(budget -> {
            BudgetDTO convertEntityToDTO = modelMapper.map(budget, BudgetDTO.class);
            convertEntityToDTO.setDepartmentDTO(modelMapper.map(budget.getDepartment(), DepartmentDTO.class));
            return convertEntityToDTO;
        }).collect(Collectors.toList());
    }

    public BudgetDTO getBudgetById(Long id) {
        Budget budgetById = budgetRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found with id " + id));
        BudgetDTO convertEntityToDTO = modelMapper.map(budgetById, BudgetDTO.class);
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(budgetById.getDepartment(), DepartmentDTO.class));
        return convertEntityToDTO;
    }

    public void deleteBudgetById(Long id) {
        Budget deleteBudget = budgetRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Budget not found with id "+id));
        budgetRepo.delete(deleteBudget);
    }
}
