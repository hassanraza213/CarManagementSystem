package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.dto.EmployeeDTO;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Employee;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.EmployeeRepo;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    private ModelMapper modelMapper;

    private DepartmentRepo departmentRepo;

    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper, DepartmentRepo departmentRepo) {
        super();
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
        this.departmentRepo = departmentRepo;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Department department = departmentRepo.findById(employeeDTO.getDepartmentId()).orElseThrow(
                () -> new EntityNotFoundException("Department not found with id " + employeeDTO.getDepartmentId()));
        Employee convertDTOToEntity = modelMapper.map(employeeDTO, Employee.class);
        convertDTOToEntity.setDepartment(department);
        Employee savedEmployee = employeeRepo.save(convertDTOToEntity);
        EmployeeDTO convertEntityToDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        return convertEntityToDTO;
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Department department = departmentRepo.findById(employeeDTO.getDepartmentId()).orElseThrow(
                () -> new EntityNotFoundException("Department not found with id " + employeeDTO.getDepartmentId()));
        Employee updateEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
        modelMapper.map(employeeDTO, updateEmployee);
        updateEmployee.setDepartment(department);
        Employee updatedEmployee = employeeRepo.save(updateEmployee);
        EmployeeDTO convertEntityToDTO = modelMapper.map(updatedEmployee, EmployeeDTO.class);
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        return convertEntityToDTO;
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> allEmployees = employeeRepo.findAll();
        return allEmployees.stream().map(employee -> {
            EmployeeDTO convertEntityToDTO = modelMapper.map(employee, EmployeeDTO.class);
            convertEntityToDTO.setDepartmentDTO(modelMapper.map(employee.getDepartment(), DepartmentDTO.class));
            return convertEntityToDTO;
        }).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employeeById = employeeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
        EmployeeDTO convertEntityToDTO = modelMapper.map(employeeById, EmployeeDTO.class);
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(employeeById.getDepartment(), DepartmentDTO.class));
        return convertEntityToDTO;
    }

    public void deleteEmployeeById(Long id) {
        Employee deleteEmployee = employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
        employeeRepo.delete(deleteEmployee);
    }
}
