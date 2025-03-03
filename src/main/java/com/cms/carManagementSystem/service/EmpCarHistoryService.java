package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.CarDTO;
import com.cms.carManagementSystem.dto.EmpCarHistoryDTO;
import com.cms.carManagementSystem.dto.EmployeeDTO;
import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.EmpCarHistory;
import com.cms.carManagementSystem.entity.Employee;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.EmpCarHistoryRepo;
import com.cms.carManagementSystem.repository.EmployeeRepo;
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
        if (empCarHistoryDTO.getStartDate() != null && empCarHistoryDTO.getEndDate() != null) {
            if (empCarHistoryDTO.getStartDate().isAfter(empCarHistoryDTO.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
        }

        Employee employee = employeeRepo.findById(empCarHistoryDTO.getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Car car = carRepo.findById(empCarHistoryDTO.getCarId()).orElseThrow(() -> new EntityNotFoundException("Car not found"));

        EmpCarHistory empCarHistory = modelMapper.map(empCarHistoryDTO, EmpCarHistory.class);
        empCarHistory.setEmployee(employee);
        empCarHistory.setCar(car);

        // Set start date and end date explicitly
        if (empCarHistoryDTO.getStartDate() != null) {
            empCarHistory.setStartDate(empCarHistoryDTO.getStartDate());
        }
        if (empCarHistoryDTO.getEndDate() != null) {
            empCarHistory.setEndDate(empCarHistoryDTO.getEndDate());
        }

        EmpCarHistory savedEmpCarHistory = empCarHistoryRepo.save(empCarHistory);

        EmpCarHistoryDTO result = modelMapper.map(savedEmpCarHistory, EmpCarHistoryDTO.class);
        result.setEmployeeId(employee.getEmployeeId());
        result.setCarId(car.getCarId());
        result.setEmployeeDTO(modelMapper.map(employee, EmployeeDTO.class));
        result.setCarDTO(modelMapper.map(car, CarDTO.class));

        return result;
    }

    public EmpCarHistoryDTO updateEmpCarHistoryDetails(Long id, EmpCarHistoryDTO empCarHistoryDTO) {
        if (empCarHistoryDTO.getStartDate() != null && empCarHistoryDTO.getEndDate() != null) {
            if (empCarHistoryDTO.getStartDate().isAfter(empCarHistoryDTO.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
        }

        EmpCarHistory existingEmpCarHistory = empCarHistoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("History not found"));
        Employee employee = employeeRepo.findById(empCarHistoryDTO.getEmployeeId()).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Car car = carRepo.findById(empCarHistoryDTO.getCarId()).orElseThrow(() -> new EntityNotFoundException("Car not found"));

        modelMapper.map(empCarHistoryDTO, existingEmpCarHistory);
        existingEmpCarHistory.setEmployee(employee);
        existingEmpCarHistory.setCar(car);

        // Set start date and end date explicitly
        if (empCarHistoryDTO.getStartDate() != null) {
            existingEmpCarHistory.setStartDate(empCarHistoryDTO.getStartDate());
        }
        if (empCarHistoryDTO.getEndDate() != null) {
            existingEmpCarHistory.setEndDate(empCarHistoryDTO.getEndDate());
        }

        EmpCarHistory updatedEmpCarHistory = empCarHistoryRepo.save(existingEmpCarHistory);

        EmpCarHistoryDTO result = modelMapper.map(updatedEmpCarHistory, EmpCarHistoryDTO.class);
        result.setEmployeeId(employee.getEmployeeId());
        result.setCarId(car.getCarId());
        result.setEmployeeDTO(modelMapper.map(employee, EmployeeDTO.class));
        result.setCarDTO(modelMapper.map(car, CarDTO.class));

        return result;
    }

    public EmpCarHistoryDTO getEmpCarHistoryDetailsById(Long id) {
        EmpCarHistory empCarHistory = empCarHistoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("History not found"));

        EmpCarHistoryDTO result = modelMapper.map(empCarHistory, EmpCarHistoryDTO.class);
        result.setEmployeeId(empCarHistory.getEmployee().getEmployeeId());
        result.setCarId(empCarHistory.getCar().getCarId());
        result.setEmployeeDTO(modelMapper.map(empCarHistory.getEmployee(), EmployeeDTO.class));
        result.setCarDTO(modelMapper.map(empCarHistory.getCar(), CarDTO.class));

        return result;
    }

    public List<EmpCarHistoryDTO> getAllEmpCarHistoryDetails() {
        List<EmpCarHistory> historyList = empCarHistoryRepo.findAll();
        return historyList.stream().map(empCarHistory -> {
            EmpCarHistoryDTO result = modelMapper.map(empCarHistory, EmpCarHistoryDTO.class);
            result.setEmployeeId(empCarHistory.getEmployee().getEmployeeId());
            result.setCarId(empCarHistory.getCar().getCarId());
            result.setEmployeeDTO(modelMapper.map(empCarHistory.getEmployee(), EmployeeDTO.class));
            result.setCarDTO(modelMapper.map(empCarHistory.getCar(), CarDTO.class));
            return result;
        }).collect(Collectors.toList());
    }

    public void deleteEmpCarHistoryDetails(Long id) {
        EmpCarHistory empCarHistory = empCarHistoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("History not found"));
        empCarHistoryRepo.delete(empCarHistory);
    }
}
