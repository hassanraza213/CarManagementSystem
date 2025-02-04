package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.*;
import com.cms.carManagementSystem.entity.*;
import com.cms.carManagementSystem.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmpCarAllocationService {

    @Autowired
    private final EmpCarAllocationRepo empCarAllocationRepo;
    private final ModelMapper modelMapper;
    private final CarRepo carRepo;
    private final EmployeeRepo employeeRepo;

    public EmpCarAllocationService(EmpCarAllocationRepo empCarAllocationRepo, ModelMapper modelMapper, CarRepo carRepo, EmployeeRepo employeeRepo) {
        this.empCarAllocationRepo = empCarAllocationRepo;
        this.modelMapper = modelMapper;
        this.carRepo = carRepo;
        this.employeeRepo = employeeRepo;
    }

    public EmpCarAllocationDTO createEmpCarAllocation(EmpCarAllocationDTO empCarAllocationDTO) {
        Employee employee = employeeRepo.findById(empCarAllocationDTO.getEmployeeId()).orElseThrow(() -> {
            log.error("Employee not found with ID: {}", empCarAllocationDTO.getEmployeeId());
            return new EntityNotFoundException("Employee not found with Id: " + empCarAllocationDTO.getEmployeeId());
        });

        Car car = carRepo.findById(empCarAllocationDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", empCarAllocationDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + empCarAllocationDTO.getCarId());
        });

        EmpCarAllocation convertEmpCarAllocationDTOToEntity = modelMapper.map(empCarAllocationDTO, EmpCarAllocation.class);
        convertEmpCarAllocationDTOToEntity.setEmployee(employee);
        convertEmpCarAllocationDTOToEntity.setCar(car);

        EmpCarAllocation saveEmpCarAllocation = empCarAllocationRepo.save(convertEmpCarAllocationDTOToEntity);
        EmpCarAllocationDTO convertEmpCarAllocationEntityToDTO = modelMapper.map(saveEmpCarAllocation, EmpCarAllocationDTO.class);

        // Manually set employeeId and carId
        convertEmpCarAllocationEntityToDTO.setEmployeeId(employee.getEmployeeId());
        convertEmpCarAllocationEntityToDTO.setCarId(car.getCarId());

        // Map Employee and include Department + Ministry
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        employeeDTO.setDepartmentDTO(departmentDTO);
        convertEmpCarAllocationEntityToDTO.setEmployeeDTO(employeeDTO);

        // Map Car and include Department + Ministry
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        convertEmpCarAllocationEntityToDTO.setCarDTO(carDTO);

        log.info("Car with id {} allotted to Employee with id {}", empCarAllocationDTO.getCarId(), empCarAllocationDTO.getEmployeeId());
        return convertEmpCarAllocationEntityToDTO;
    }

    public EmpCarAllocationDTO updateEmpCarAllocationDetails(Long id, EmpCarAllocationDTO empCarAllocationDTO) {
        EmpCarAllocation existingAllocation = empCarAllocationRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Allocation not found"));

        Employee employee = employeeRepo.findById(empCarAllocationDTO.getEmployeeId()).orElseThrow(() -> {
            log.error("Employee not found with ID: {}", empCarAllocationDTO.getEmployeeId());
            return new EntityNotFoundException("Employee not found with Id: " + empCarAllocationDTO.getEmployeeId());
        });

        Car car = carRepo.findById(empCarAllocationDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", empCarAllocationDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + empCarAllocationDTO.getCarId());
        });

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(empCarAllocationDTO, existingAllocation);
        existingAllocation.setEmployee(employee);
        existingAllocation.setCar(car);

        EmpCarAllocation saveEmpCarAllocation = empCarAllocationRepo.save(existingAllocation);

        // Manually set employeeId and carId
        EmpCarAllocationDTO updatedDTO = modelMapper.map(saveEmpCarAllocation, EmpCarAllocationDTO.class);
        updatedDTO.setEmployeeId(employee.getEmployeeId());
        updatedDTO.setCarId(car.getCarId());

        // Map Employee and include Department + Ministry
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        employeeDTO.setDepartmentDTO(departmentDTO);
        updatedDTO.setEmployeeDTO(employeeDTO);

        // Map Car and include Department + Ministry
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        updatedDTO.setCarDTO(carDTO);

        return updatedDTO;
    }

    public EmpCarAllocationDTO getEmpCarAllocationDetailsById(Long id) {
        EmpCarAllocation existingAllocation = empCarAllocationRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Allocation not found"));

        EmpCarAllocationDTO convertEmpCarAllocationEntityToDTO = modelMapper.map(existingAllocation, EmpCarAllocationDTO.class);

        // Manually set employeeId and carId
        convertEmpCarAllocationEntityToDTO.setEmployeeId(existingAllocation.getEmployee().getEmployeeId());
        convertEmpCarAllocationEntityToDTO.setCarId(existingAllocation.getCar().getCarId());

        // Map Employee and include Department + Ministry
        Employee employee = existingAllocation.getEmployee();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        employeeDTO.setDepartmentDTO(departmentDTO);
        convertEmpCarAllocationEntityToDTO.setEmployeeDTO(employeeDTO);

        // Map Car and include Department + Ministry
        Car car = existingAllocation.getCar();
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        convertEmpCarAllocationEntityToDTO.setCarDTO(carDTO);

        return convertEmpCarAllocationEntityToDTO;
    }

    public List<EmpCarAllocationDTO> getAllEmpCarAllocationDetails() {
        List<EmpCarAllocation> empCarAllocationList = empCarAllocationRepo.findAll();
        return empCarAllocationList.stream().map(empCarAllocation -> {
            EmpCarAllocationDTO convertEmpCarAllocationEntityToDTO = modelMapper.map(empCarAllocation, EmpCarAllocationDTO.class);

            // Manually set employeeId and carId
            convertEmpCarAllocationEntityToDTO.setEmployeeId(empCarAllocation.getEmployee().getEmployeeId());
            convertEmpCarAllocationEntityToDTO.setCarId(empCarAllocation.getCar().getCarId());

            // Map Employee and include Department + Ministry
            Employee employee = empCarAllocation.getEmployee();
            EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
            Department department = employee.getDepartment();
            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
            departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            employeeDTO.setDepartmentDTO(departmentDTO);
            convertEmpCarAllocationEntityToDTO.setEmployeeDTO(employeeDTO);

            // Map Car and include Department + Ministry
            Car car = empCarAllocation.getCar();
            CarDTO carDTO = modelMapper.map(car, CarDTO.class);
            Department carDepartment = car.getDepartment();
            DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
            carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
            carDTO.setDepartmentDTO(carDepartmentDTO);
            convertEmpCarAllocationEntityToDTO.setCarDTO(carDTO);

            return convertEmpCarAllocationEntityToDTO;
        }).collect(Collectors.toList());
    }

    public void deleteEmpCarAllocationDetails(Long id) {
        EmpCarAllocation deleteEmpCarAllocation = empCarAllocationRepo.findById(id).orElseThrow(() -> {
            log.error("Employee car allocation details not found with Id: {}", id);
            return new EntityNotFoundException("Employee car allocation details not found with Id: " + id);
        });
        empCarAllocationRepo.delete(deleteEmpCarAllocation);
        log.info("Employee car allocation details with Id {} deleted successfully", id);
    }
}