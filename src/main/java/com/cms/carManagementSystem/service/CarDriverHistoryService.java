package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.*;
import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.CarDriverHistory;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Driver;
import com.cms.carManagementSystem.repository.CarDriverHistoryRepo;
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
public class CarDriverHistoryService {

    @Autowired
    private final CarDriverHistoryRepo carDriverHistoryRepo;
    private final ModelMapper modelMapper;
    private final CarRepo carRepo;
    private final DriverRepo driverRepo;

    public CarDriverHistoryService(CarDriverHistoryRepo carDriverHistoryRepo, ModelMapper modelMapper, CarRepo carRepo, DriverRepo driverRepo) {
        this.carDriverHistoryRepo = carDriverHistoryRepo;
        this.modelMapper = modelMapper;
        this.carRepo = carRepo;
        this.driverRepo = driverRepo;
    }

    public CarDriverHistoryDTO createCarDriverHistory(CarDriverHistoryDTO carDriverHistoryDTO) {
        Driver driver = driverRepo.findById(carDriverHistoryDTO.getDriverId()).orElseThrow(() -> {
            log.error("Driver not found with ID: {}", carDriverHistoryDTO.getDriverId());
            return new EntityNotFoundException("Driver not found with Id: " + carDriverHistoryDTO.getDriverId());
        });

        Car car = carRepo.findById(carDriverHistoryDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", carDriverHistoryDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + carDriverHistoryDTO.getCarId());
        });

        CarDriverHistory carDriverHistoryEntity = modelMapper.map(carDriverHistoryDTO, CarDriverHistory.class);
        carDriverHistoryEntity.setDriver(driver);
        carDriverHistoryEntity.setCar(car);

        CarDriverHistory savedCarDriverHistory = carDriverHistoryRepo.save(carDriverHistoryEntity);
        CarDriverHistoryDTO carDriverHistoryDTOResponse = modelMapper.map(savedCarDriverHistory, CarDriverHistoryDTO.class);

        carDriverHistoryDTOResponse.setDriverId(driver.getDriverId());
        carDriverHistoryDTOResponse.setCarId(car.getCarId());

        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        Department driverDepartment = driver.getDepartment();
        DepartmentDTO driverDepartmentDTO = modelMapper.map(driverDepartment, DepartmentDTO.class);
        driverDepartmentDTO.setMinistryDTO(modelMapper.map(driverDepartment.getMinistry(), MinistryDTO.class));
        driverDTO.setDepartmentDTO(driverDepartmentDTO);
        carDriverHistoryDTOResponse.setDriverDTO(driverDTO);

        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        carDriverHistoryDTOResponse.setCarDTO(carDTO);

        log.info("Car with id {} added to history of Driver with id {}", carDriverHistoryDTO.getCarId(), carDriverHistoryDTO.getDriverId());
        return carDriverHistoryDTOResponse;
    }

    public CarDriverHistoryDTO updateCarDriverHistory(Long id, CarDriverHistoryDTO carDriverHistoryDTO) {
        CarDriverHistory existingHistory = carDriverHistoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("History not found"));

        Driver driver = driverRepo.findById(carDriverHistoryDTO.getDriverId()).orElseThrow(() -> {
            log.error("Driver not found with ID: {}", carDriverHistoryDTO.getDriverId());
            return new EntityNotFoundException("Driver not found with Id: " + carDriverHistoryDTO.getDriverId());
        });

        Car car = carRepo.findById(carDriverHistoryDTO.getCarId()).orElseThrow(() -> {
            log.error("Car not found with ID: {}", carDriverHistoryDTO.getCarId());
            return new EntityNotFoundException("Car not found with Id: " + carDriverHistoryDTO.getCarId());
        });

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(carDriverHistoryDTO, existingHistory);
        existingHistory.setDriver(driver);
        existingHistory.setCar(car);

        CarDriverHistory savedCarDriverHistory = carDriverHistoryRepo.save(existingHistory);

        // Manually set driverId and carId
        CarDriverHistoryDTO updatedDTO = modelMapper.map(savedCarDriverHistory, CarDriverHistoryDTO.class);
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

    public CarDriverHistoryDTO getCarDriverHistoryById(Long id) {
        CarDriverHistory existingHistory = carDriverHistoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("History not found"));

        CarDriverHistoryDTO carDriverHistoryDTO = modelMapper.map(existingHistory, CarDriverHistoryDTO.class);

        // Manually set driverId and carId
        carDriverHistoryDTO.setDriverId(existingHistory.getDriver().getDriverId());
        carDriverHistoryDTO.setCarId(existingHistory.getCar().getCarId());

        // Map Driver and include Department + Ministry
        Driver driver = existingHistory.getDriver();
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        Department driverDepartment = driver.getDepartment();
        DepartmentDTO driverDepartmentDTO = modelMapper.map(driverDepartment, DepartmentDTO.class);
        driverDepartmentDTO.setMinistryDTO(modelMapper.map(driverDepartment.getMinistry(), MinistryDTO.class));
        driverDTO.setDepartmentDTO(driverDepartmentDTO);
        carDriverHistoryDTO.setDriverDTO(driverDTO);

        // Map Car and include Department + Ministry
        Car car = existingHistory.getCar();
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        Department carDepartment = car.getDepartment();
        DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
        carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
        carDTO.setDepartmentDTO(carDepartmentDTO);
        carDriverHistoryDTO.setCarDTO(carDTO);

        return carDriverHistoryDTO;
    }

    public List<CarDriverHistoryDTO> getAllCarDriverHistoryDetails() {
        List<CarDriverHistory> carDriverHistoryList = carDriverHistoryRepo.findAll();
        return carDriverHistoryList.stream().map(carDriverHistory -> {
            CarDriverHistoryDTO carDriverHistoryDTO = modelMapper.map(carDriverHistory, CarDriverHistoryDTO.class);

            // Manually set driverId and carId
            carDriverHistoryDTO.setDriverId(carDriverHistory.getDriver().getDriverId());
            carDriverHistoryDTO.setCarId(carDriverHistory.getCar().getCarId());

            // Map Driver and include Department + Ministry
            Driver driver = carDriverHistory.getDriver();
            DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
            Department driverDepartment = driver.getDepartment();
            DepartmentDTO driverDepartmentDTO = modelMapper.map(driverDepartment, DepartmentDTO.class);
            driverDepartmentDTO.setMinistryDTO(modelMapper.map(driverDepartment.getMinistry(), MinistryDTO.class));
            driverDTO.setDepartmentDTO(driverDepartmentDTO);
            carDriverHistoryDTO.setDriverDTO(driverDTO);

            // Map Car and include Department + Ministry
            Car car = carDriverHistory.getCar();
            CarDTO carDTO = modelMapper.map(car, CarDTO.class);
            Department carDepartment = car.getDepartment();
            DepartmentDTO carDepartmentDTO = modelMapper.map(carDepartment, DepartmentDTO.class);
            carDepartmentDTO.setMinistryDTO(modelMapper.map(carDepartment.getMinistry(), MinistryDTO.class));
            carDTO.setDepartmentDTO(carDepartmentDTO);
            carDriverHistoryDTO.setCarDTO(carDTO);

            return carDriverHistoryDTO;
        }).collect(Collectors.toList());
    }

    public void deleteCarDriverHistory(Long id) {
        CarDriverHistory carDriverHistory = carDriverHistoryRepo.findById(id).orElseThrow(() -> {
            log.error("Car driver history details not found with Id: {}", id);
            return new EntityNotFoundException("Car driver history details not found with Id: " + id);
        });
        carDriverHistoryRepo.delete(carDriverHistory);
        log.info("Car driver history details with Id {} deleted successfully", id);
    }
}
