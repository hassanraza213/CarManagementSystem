package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.CarDTO;
import com.cms.carManagementSystem.dto.MaintenanceDTO;
import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.Maintenance;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.MaintenanceRepo;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    @Autowired
    private final MaintenanceRepo maintenanceRepo;


    private final CarRepo carRepo;

    private ModelMapper modelMapper;

    public MaintenanceService(MaintenanceRepo maintenanceRepo, ModelMapper modelMapper, CarRepo carRepo) {
        this.maintenanceRepo = maintenanceRepo;
        this.modelMapper = modelMapper;
        this.carRepo = carRepo;
    }

    public MaintenanceDTO createMaintenance(MaintenanceDTO maintenanceDTO) {
        Car car = carRepo.findById(maintenanceDTO.getCarId()).orElseThrow(
                () -> new EntityNotFoundException("Car not found with id " + maintenanceDTO.getCarId()));
        Maintenance maintenance = modelMapper.map(maintenanceDTO, Maintenance.class);
        maintenance.setCar(car);
        Maintenance savedMaintenance = maintenanceRepo.save(maintenance);
        MaintenanceDTO maintenanceDTOResponse = modelMapper.map(savedMaintenance, MaintenanceDTO.class);
        maintenanceDTOResponse.setCarDTO(modelMapper.map(car, CarDTO.class));
        return maintenanceDTOResponse;
    }

    public MaintenanceDTO updateMaintenance(Long id, MaintenanceDTO maintenanceDTO) {
        Car car = carRepo.findById(maintenanceDTO.getCarId()).orElseThrow(
                () -> new EntityNotFoundException("Car not found with id " + maintenanceDTO.getCarId()));
        Maintenance maintenanceToUpdate = maintenanceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found with id " + id));
        modelMapper.map(maintenanceDTO, maintenanceToUpdate);
        maintenanceToUpdate.setCar(car);
        Maintenance updatedMaintenance = maintenanceRepo.save(maintenanceToUpdate);
        MaintenanceDTO maintenanceDTOResponse = modelMapper.map(updatedMaintenance, MaintenanceDTO.class);
        maintenanceDTOResponse.setCarDTO(modelMapper.map(car, CarDTO.class));
        return maintenanceDTOResponse;
    }

    public List<MaintenanceDTO> getAllMaintenances() {
        List<Maintenance> allMaintenances = maintenanceRepo.findAll();
        return allMaintenances.stream().map(maintenance -> {
            MaintenanceDTO maintenanceDTO = modelMapper.map(maintenance, MaintenanceDTO.class);
            maintenanceDTO.setCarDTO(modelMapper.map(maintenance.getCar(), CarDTO.class));
            return maintenanceDTO;
        }).collect(Collectors.toList());
    }

    public MaintenanceDTO getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found with id " + id));
        MaintenanceDTO maintenanceDTO = modelMapper.map(maintenance, MaintenanceDTO.class);
        maintenanceDTO.setCarDTO(modelMapper.map(maintenance.getCar(), CarDTO.class));
        return maintenanceDTO;
    }

    public void deleteMaintenanceById(Long id) {
        Maintenance maintenanceToDelete = maintenanceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found with id " + id));
        maintenanceRepo.delete(maintenanceToDelete);
    }
}
