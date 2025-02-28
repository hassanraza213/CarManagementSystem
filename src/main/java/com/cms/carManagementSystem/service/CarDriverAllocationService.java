package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.*;
import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.CarDriverAllocation;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Driver;
import com.cms.carManagementSystem.repository.CarDriverAllocationRepo;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.DriverRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarDriverAllocationService {

    @Autowired
    private final CarDriverAllocationRepo carDriverAllocationRepo;
    private final ModelMapper modelMapper;
    private final CarRepo carRepo;
    private final DriverRepo driverRepo;

    public CarDriverAllocationService(CarDriverAllocationRepo carDriverAllocationRepo, ModelMapper modelMapper, CarRepo carRepo, DriverRepo driverRepo) {
        this.carDriverAllocationRepo = carDriverAllocationRepo;
        this.modelMapper = modelMapper;
        this.carRepo = carRepo;
        this.driverRepo = driverRepo;
    }

    public CarDriverAllocationDTO createCarDriverAllocation(CarDriverAllocationDTO carDriverAllocationDTO) {
        Driver driver = driverRepo.findById(carDriverAllocationDTO.getDriverId()).orElseThrow(() -> {
            log.error("Driver not found with ID: {}", carDriverAllocationDTO.getDriverId());
            return new EntityNotFoundException("Driver not found with Id: " + carDriverAllocationDTO.getDriverId());
        });

        Car car = carRepo.findById(carDriverAllocationDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", carDriverAllocationDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + carDriverAllocationDTO.getCarId());
        });

        CarDriverAllocation convertCarDriverAllocationDTOToEntity = modelMapper.map(carDriverAllocationDTO, CarDriverAllocation.class);
        convertCarDriverAllocationDTOToEntity.setDriver(driver);
        convertCarDriverAllocationDTOToEntity.setCar(car);

        CarDriverAllocation saveCarDriverAllocation = carDriverAllocationRepo.save(convertCarDriverAllocationDTOToEntity);
        CarDriverAllocationDTO convertCarDriverAllocationEntityToDTO = modelMapper.map(saveCarDriverAllocation, CarDriverAllocationDTO.class);

        // Manually set driverId and carId
        convertCarDriverAllocationEntityToDTO.setDriverId(driver.getDriverId());
        convertCarDriverAllocationEntityToDTO.setCarId(car.getCarId());

        // Map Driver and include Department + Ministry
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        Department driverDepartment = driver.getDepartment();
        DepartmentDTO driverDepartmentDTO = modelMapper.map(driverDepartment, DepartmentDTO.class);
        driverDepartmentDTO.setMinistryDTO(modelMapper.map(driverDepartment.getMinistry(), MinistryDTO.class));
        driverDTO.setDepartmentDTO(driverDepartmentDTO);
        convertCarDriverAllocationEntityToDTO.setDriverDTO(driverDTO);

        // Map Car and include Department + Ministry
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        convertCarDriverAllocationEntityToDTO.setCarDTO(carDTO);

        log.info("Car with id {} allocated to Driver with id {}", carDriverAllocationDTO.getCarId(), carDriverAllocationDTO.getDriverId());
        return convertCarDriverAllocationEntityToDTO;
    }

    public CarDriverAllocationDTO updateCarDriverAllocationDetails(Long id, CarDriverAllocationDTO carDriverAllocationDTO) {
        CarDriverAllocation existingAllocation = carDriverAllocationRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Allocation not found"));

        Driver driver = driverRepo.findById(carDriverAllocationDTO.getDriverId()).orElseThrow(() -> {
            log.error("Driver not found with ID: {}", carDriverAllocationDTO.getDriverId());
            return new EntityNotFoundException("Driver not found with Id: " + carDriverAllocationDTO.getDriverId());
        });

        Car car = carRepo.findById(carDriverAllocationDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", carDriverAllocationDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + carDriverAllocationDTO.getCarId());
        });

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(carDriverAllocationDTO, existingAllocation);
        existingAllocation.setDriver(driver);
        existingAllocation.setCar(car);

        CarDriverAllocation saveCarDriverAllocation = carDriverAllocationRepo.save(existingAllocation);

        // Manually set driverId and carId
        CarDriverAllocationDTO updatedDTO = modelMapper.map(saveCarDriverAllocation, CarDriverAllocationDTO.class);
        updatedDTO.setDriverId(driver.getDriverId());
        updatedDTO.setCarId(car.getCarId());

        // Map Driver and include Department + Ministry
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        Department driverDepartment = driver.getDepartment();
        DepartmentDTO driverDepartmentDTO = modelMapper.map(driverDepartment, DepartmentDTO.class);
        driverDepartmentDTO.setMinistryDTO(modelMapper.map(driverDepartment.getMinistry(), MinistryDTO.class));
        driverDTO.setDepartmentDTO(driverDepartmentDTO);
        updatedDTO.setDriverDTO(driverDTO);

        // Map Car and include Department + Ministry
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        updatedDTO.setCarDTO(carDTO);

        return updatedDTO;
    }

    public CarDriverAllocationDTO getCarDriverAllocationDetailsById(Long id) {
        CarDriverAllocation existingAllocation = carDriverAllocationRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Allocation not found"));

        CarDriverAllocationDTO convertCarDriverAllocationEntityToDTO = modelMapper.map(existingAllocation, CarDriverAllocationDTO.class);

        // Manually set driverId and carId
        convertCarDriverAllocationEntityToDTO.setDriverId(existingAllocation.getDriver().getDriverId());
        convertCarDriverAllocationEntityToDTO.setCarId(existingAllocation.getCar().getCarId());

        // Map Driver and include Department + Ministry
        Driver driver = existingAllocation.getDriver();
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        Department driverDepartment = driver.getDepartment();
        DepartmentDTO driverDepartmentDTO = modelMapper.map(driverDepartment, DepartmentDTO.class);
        driverDepartmentDTO.setMinistryDTO(modelMapper.map(driverDepartment.getMinistry(), MinistryDTO.class));
        driverDTO.setDepartmentDTO(driverDepartmentDTO);
        convertCarDriverAllocationEntityToDTO.setDriverDTO(driverDTO);

        // Map Car and include Department + Ministry
        Car car = existingAllocation.getCar();
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        convertCarDriverAllocationEntityToDTO.setCarDTO(carDTO);

        return convertCarDriverAllocationEntityToDTO;
    }

    public List<CarDriverAllocationDTO> getAllCarDriverAllocationDetails() {
        List<CarDriverAllocation> carDriverAllocationList = carDriverAllocationRepo.findAll();
        return carDriverAllocationList.stream().map(carDriverAllocation -> {
            CarDriverAllocationDTO convertCarDriverAllocationEntityToDTO = modelMapper.map(carDriverAllocation, CarDriverAllocationDTO.class);

            // Manually set driverId and carId
            convertCarDriverAllocationEntityToDTO.setDriverId(carDriverAllocation.getDriver().getDriverId());
            convertCarDriverAllocationEntityToDTO.setCarId(carDriverAllocation.getCar().getCarId());

            // Map Driver and include Department + Ministry
            Driver driver = carDriverAllocation.getDriver();
            DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
            Department driverDepartment = driver.getDepartment();
            DepartmentDTO driverDepartmentDTO = modelMapper.map(driverDepartment, DepartmentDTO.class);
            driverDepartmentDTO.setMinistryDTO(modelMapper.map(driverDepartment.getMinistry(), MinistryDTO.class));
            driverDTO.setDepartmentDTO(driverDepartmentDTO);
            convertCarDriverAllocationEntityToDTO.setDriverDTO(driverDTO);

            // Map Car and include Department + Ministry
            Car car = carDriverAllocation.getCar();
            CarDTO carDTO = modelMapper.map(car, CarDTO.class);
            Department carDepartment = car.getDepartment();
            DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
            carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
            carDTO.setDepartmentDTO(carDepartmentDTO);
            convertCarDriverAllocationEntityToDTO.setCarDTO(carDTO);

            return convertCarDriverAllocationEntityToDTO;
        }).collect(Collectors.toList());
    }

    public void deleteCarDriverAllocationDetails(Long id) {
        CarDriverAllocation deleteCarDriverAllocation = carDriverAllocationRepo.findById(id).orElseThrow(() -> {
            log.error("Car driver allocation details not found with Id: {}", id);
            return new EntityNotFoundException("Car driver allocation details not found with Id: " + id);
        });
        carDriverAllocationRepo.delete(deleteCarDriverAllocation);
        log.info("Car driver allocation details with Id {} deleted successfully", id);
    }
}
