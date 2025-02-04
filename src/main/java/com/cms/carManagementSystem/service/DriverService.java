package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.dto.DriverDTO;
import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Driver;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.DriverRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DriverService {

    @Autowired
    private final DriverRepo driverRepo;

    private final ModelMapper modelMapper;

    private final DepartmentRepo departmentRepo;

    public DriverService(DriverRepo driverRepo, ModelMapper modelMapper, DepartmentRepo departmentRepo) {
        this.driverRepo = driverRepo;
        this.modelMapper = modelMapper;
        this.departmentRepo = departmentRepo;
    }

    public DriverDTO createDriver(DriverDTO driverDTO) {
        log.info("Adding a new Driver with details: {}", driverDTO);

        Department department = departmentRepo.findById(driverDTO.getDepartmentId()).orElseThrow(() -> {
            log.error("Department not found with id: {} ", driverDTO.getDepartmentId());
            return new EntityNotFoundException("Department not found with id " + driverDTO.getDepartmentId());
        });

        Driver convertDTOToEntity = modelMapper.map(driverDTO, Driver.class);
        convertDTOToEntity.setDepartment(department);
        log.info("Saving driver with details: {}", convertDTOToEntity);
        Driver savedDriver = driverRepo.save(convertDTOToEntity);

        DriverDTO convertEntityTODTO = modelMapper.map(savedDriver, DriverDTO.class);
        convertEntityTODTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        log.info("Driver added with ID: {}", savedDriver.getDriverId());
        return convertEntityTODTO;
    }

    public DriverDTO updateDriver(Long id, DriverDTO driverDTO) {
        log.info("updating driver details with ID: {}", id);
        Department department = departmentRepo.findById(driverDTO.getDepartmentId()).orElseThrow(() -> {
            log.error("Department not found with ID: {}", driverDTO.getDepartmentId());
            return new EntityNotFoundException("Department not found with id " + driverDTO.getDepartmentId());
        });

        Driver updateDriver = driverRepo.findById(id).orElseThrow(() -> {
            log.error("Driver not found with Id: {}", id);
            return new EntityNotFoundException("Driver not found With id " + id);
        });

        log.info("Mapping DTO to Entity: {}", driverDTO);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(driverDTO, updateDriver);
        updateDriver.setDepartment(department);

        log.info("Saving updated driver: {}", updateDriver);
        Driver updatedDriver = driverRepo.save(updateDriver);

        DriverDTO convertEntityToDTO = modelMapper.map(updatedDriver, DriverDTO.class);
        Department department1 = updatedDriver.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department1, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department1.getMinistry(), MinistryDTO.class));
        convertEntityToDTO.setDepartmentId(department.getDepartmentId());
        convertEntityToDTO.setDepartmentDTO(departmentDTO);
        convertEntityToDTO.getDepartmentDTO().setMinistryId(department.getMinistry().getMinistryId());
        log.info("Driver updated with Id: {}", updatedDriver.getDriverId());
        return convertEntityToDTO;
    }

    public List<DriverDTO> getAllDrivers() {
        log.info("Fetching all drivers");
        List<Driver> allDrivers = driverRepo.findAll();
        List<DriverDTO> driverDTOList = allDrivers.stream().map(driver -> {
            DriverDTO convertEntityToDTO = modelMapper.map(driver, DriverDTO.class);
            Department department = driver.getDepartment();
            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
            departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            convertEntityToDTO.setDepartmentDTO(departmentDTO);
            return convertEntityToDTO;
        }).collect(Collectors.toList());

        log.info("Retrieved {} departments ", driverDTOList.size());
        return driverDTOList;
    }

    public DriverDTO getDriverById(Long id) {
        Driver getDriver = driverRepo.findById(id).orElseThrow(() -> {
            log.error("Driver not found with Id: {}", id);
            return new EntityNotFoundException("Driver not found with Id " + id);
        });

        DriverDTO convertEntityToDTO = modelMapper.map(getDriver, DriverDTO.class);
        Department department = getDriver.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        convertEntityToDTO.setDepartmentDTO(departmentDTO);

        log.info("Driver found: {}", getDriver.getDriverName());
        return convertEntityToDTO;
    }

    public void deleteDriverById(Long id) {
        Driver deleteDriver = driverRepo.findById(id).orElseThrow(() -> {
            log.error("Driver not found with Id: {}", id);
            return new EntityNotFoundException("Driver not found with Id " + id);
        });

        driverRepo.delete(deleteDriver);
        log.info("Driver with Id {} deleted", id);
    }
}
