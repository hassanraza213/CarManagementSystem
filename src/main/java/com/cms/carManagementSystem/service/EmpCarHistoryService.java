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
public class EmpCarHistoryService {

    @Autowired
    private final EmpCarHistoryRepo empCarHistoryRepo;
    private final ModelMapper modelMapper;
    private final CarRepo carRepo;
    private final EmployeeRepo employeeRepo;

    public EmpCarHistoryService(EmpCarHistoryRepo empCarHistoryRepo, ModelMapper modelMapper, CarRepo carRepo, EmployeeRepo employeeRepo) {
        this.empCarHistoryRepo = empCarHistoryRepo;
        this.modelMapper = modelMapper;
        this.carRepo = carRepo;
        this.employeeRepo = employeeRepo;
    }

    public EmpCarHistoryDTO createEmpCarHistory(EmpCarHistoryDTO empCarHistoryDTO) {
        Employee employee = employeeRepo.findById(empCarHistoryDTO.getEmployeeId()).orElseThrow(() -> {
            log.error("Employee not found with ID: {}", empCarHistoryDTO.getEmployeeId());
            return new EntityNotFoundException("Employee not found with Id: " + empCarHistoryDTO.getEmployeeId());
        });

        Car car = carRepo.findById(empCarHistoryDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", empCarHistoryDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + empCarHistoryDTO.getCarId());
        });

        EmpCarHistory convertEmpCarHistoryDTOToEntity = modelMapper.map(empCarHistoryDTO, EmpCarHistory.class);
        convertEmpCarHistoryDTOToEntity.setEmployee(employee);
        convertEmpCarHistoryDTOToEntity.setCar(car);

        EmpCarHistory saveEmpCarHistory = empCarHistoryRepo.save(convertEmpCarHistoryDTOToEntity);
        EmpCarHistoryDTO convertEmpCarHistoryEntityToDTO = modelMapper.map(saveEmpCarHistory, EmpCarHistoryDTO.class);

        convertEmpCarHistoryEntityToDTO.setEmployeeId(employee.getEmployeeId());
        convertEmpCarHistoryEntityToDTO.setCarId(car.getCarId());

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        employeeDTO.setDepartmentDTO(departmentDTO);
        convertEmpCarHistoryEntityToDTO.setEmployeeDTO(employeeDTO);

        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        convertEmpCarHistoryEntityToDTO.setCarDTO(carDTO);

        log.info("Car with id {} history added for Employee with id {}", empCarHistoryDTO.getCarId(), empCarHistoryDTO.getEmployeeId());
        return convertEmpCarHistoryEntityToDTO;
    }

    public EmpCarHistoryDTO updateEmpCarHistoryDetails(Long id, EmpCarHistoryDTO empCarHistoryDTO) {
        EmpCarHistory existingHistory = empCarHistoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("History not found"));

        Employee employee = employeeRepo.findById(empCarHistoryDTO.getEmployeeId()).orElseThrow(() -> {
            log.error("Employee not found with ID: {}", empCarHistoryDTO.getEmployeeId());
            return new EntityNotFoundException("Employee not found with Id: " + empCarHistoryDTO.getEmployeeId());
        });

        Car car = carRepo.findById(empCarHistoryDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", empCarHistoryDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + empCarHistoryDTO.getCarId());
        });

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(empCarHistoryDTO, existingHistory);
        existingHistory.setEmployee(employee);
        existingHistory.setCar(car);

        EmpCarHistory saveEmpCarHistory = empCarHistoryRepo.save(existingHistory);

        EmpCarHistoryDTO updatedDTO = modelMapper.map(saveEmpCarHistory, EmpCarHistoryDTO.class);
        updatedDTO.setEmployeeId(employee.getEmployeeId());
        updatedDTO.setCarId(car.getCarId());

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        employeeDTO.setDepartmentDTO(departmentDTO);
        updatedDTO.setEmployeeDTO(employeeDTO);

        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        updatedDTO.setCarDTO(carDTO);

        return updatedDTO;
    }

    public EmpCarHistoryDTO getEmpCarHistoryDetailsById(Long id) {
        EmpCarHistory existingHistory = empCarHistoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("History not found"));

        EmpCarHistoryDTO convertEmpCarHistoryEntityToDTO = modelMapper.map(existingHistory, EmpCarHistoryDTO.class);

        convertEmpCarHistoryEntityToDTO.setEmployeeId(existingHistory.getEmployee().getEmployeeId());
        convertEmpCarHistoryEntityToDTO.setCarId(existingHistory.getCar().getCarId());

        Employee employee = existingHistory.getEmployee();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        employeeDTO.setDepartmentDTO(departmentDTO);
        convertEmpCarHistoryEntityToDTO.setEmployeeDTO(employeeDTO);

        Car car = existingHistory.getCar();
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        convertEmpCarHistoryEntityToDTO.setCarDTO(carDTO);

        return convertEmpCarHistoryEntityToDTO;
    }

    public List<EmpCarHistoryDTO> getAllEmpCarHistoryDetails() {
        List<EmpCarHistory> empCarHistoryList = empCarHistoryRepo.findAll();
        return empCarHistoryList.stream().map(empCarHistory -> {
            EmpCarHistoryDTO convertEmpCarHistoryEntityToDTO = modelMapper.map(empCarHistory, EmpCarHistoryDTO.class);

            convertEmpCarHistoryEntityToDTO.setEmployeeId(empCarHistory.getEmployee().getEmployeeId());
            convertEmpCarHistoryEntityToDTO.setCarId(empCarHistory.getCar().getCarId());

            Employee employee = empCarHistory.getEmployee();
            EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
            Department department = employee.getDepartment();
            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
            departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            employeeDTO.setDepartmentDTO(departmentDTO);
            convertEmpCarHistoryEntityToDTO.setEmployeeDTO(employeeDTO);

            Car car = empCarHistory.getCar();
            CarDTO carDTO = modelMapper.map(car, CarDTO.class);
            Department carDepartment = car.getDepartment();
            DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
            carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
            carDTO.setDepartmentDTO(carDepartmentDTO);
            convertEmpCarHistoryEntityToDTO.setCarDTO(carDTO);

            return convertEmpCarHistoryEntityToDTO;
        }).collect(Collectors.toList());
    }

    public void deleteEmpCarHistoryDetails(Long id) {
        EmpCarHistory deleteEmpCarHistory = empCarHistoryRepo.findById(id).orElseThrow(() -> {
            log.error("Employee car history details not found with Id: {}", id);
            return new EntityNotFoundException("Employee car history details not found with Id: " + id);
        });
        empCarHistoryRepo.delete(deleteEmpCarHistory);
        log.info("Employee car history details with Id {} deleted successfully", id);
    }
}
