package com.cms.carManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import com.cms.carManagementSystem.dto.MinistryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.dto.EmployeeDTO;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Employee;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.EmployeeRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private final EmployeeRepo employeeRepo;

    private final ModelMapper modelMapper;

    private final DepartmentRepo departmentRepo;

    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper, DepartmentRepo departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
        this.departmentRepo = departmentRepo;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating employee with data: {}", employeeDTO);
        Department department = departmentRepo.findById(employeeDTO.getDepartmentId()).orElseThrow(() -> {
            log.error("Department not found with id {}", employeeDTO.getDepartmentId());
            return new EntityNotFoundException("Department not found with id " + employeeDTO.getDepartmentId());
        });
        Employee convertDTOToEntity = modelMapper.map(employeeDTO, Employee.class);
        convertDTOToEntity.setDepartment(department);
        Employee savedEmployee = employeeRepo.save(convertDTOToEntity);
        log.info("Employee created successfully with ID: {}", savedEmployee.getEmployeeId());
        EmployeeDTO convertEntityToDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        convertEntityToDTO.setDepartmentDTO(departmentDTO);
        return convertEntityToDTO;
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", id);
        Department department = departmentRepo.findById(employeeDTO.getDepartmentId()).orElseThrow(() -> {
            log.error("Department not found with id {}", employeeDTO.getDepartmentId());
            return new EntityNotFoundException("Department not found with id " + employeeDTO.getDepartmentId());
        });
        Employee updateEmployee = employeeRepo.findById(id).orElseThrow(() -> {
            log.error("Employee not found with id {}", id);
            return new EntityNotFoundException("Employee not found with id " + id);
        });
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(employeeDTO, updateEmployee);
        updateEmployee.setDepartment(department);

        Employee updatedEmployee = employeeRepo.save(updateEmployee);
        log.info("Employee with ID: {} updated successfully", id);

        EmployeeDTO convertEntityToDTO = modelMapper.map(updatedEmployee, EmployeeDTO.class);
        convertEntityToDTO.setDepartmentId(department.getDepartmentId());
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        convertEntityToDTO.getDepartmentDTO().setMinistryDTO(
                modelMapper.map(department.getMinistry(), MinistryDTO.class)
        );
        convertEntityToDTO.getDepartmentDTO().setMinistryId(department.getMinistry().getMinistryId());
        return convertEntityToDTO;
    }

    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");
        List<Employee> allEmployees = employeeRepo.findAll();
        return allEmployees.stream().map(employee -> {
            EmployeeDTO convertEntityToDTO = modelMapper.map(employee, EmployeeDTO.class);
            Department department = employee.getDepartment();
            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
            departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            convertEntityToDTO.setDepartmentDTO(departmentDTO);
            log.info("Fetched employee with ID: {}", employee.getEmployeeId());
            return convertEntityToDTO;
        }).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee by ID: {}", id);
        Employee employeeById = employeeRepo.findById(id).orElseThrow(() -> {
            log.error("Employee not found with id {}", id);
            return new EntityNotFoundException("Employee not found with id " + id);
        });
        EmployeeDTO convertEntityToDTO = modelMapper.map(employeeById, EmployeeDTO.class);
        Department department = employeeById.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        convertEntityToDTO.setDepartmentDTO(departmentDTO);
        log.info("Fetched employee with ID: {}", id);
        return convertEntityToDTO;
    }

    public void deleteEmployeeById(Long id) {
        log.info("Deleting employee with ID: {}", id);
        Employee deleteEmployee = employeeRepo.findById(id).orElseThrow(() -> {
            log.error("Employee not found with id {}", id);
            return new EntityNotFoundException("Employee not found with id " + id);
        });
        employeeRepo.delete(deleteEmployee);
        log.info("Employee with ID: {} deleted successfully", id);
    }
}
