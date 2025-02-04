package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.CarDTO;
import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService {

    @Autowired
    private final CarRepo carRepo;
    private final ModelMapper modelMapper;
    private final DepartmentRepo departmentRepo;

    public CarService(CarRepo carRepo, ModelMapper modelMapper, DepartmentRepo departmentRepo) {
        this.carRepo = carRepo;
        this.modelMapper = modelMapper;
        this.departmentRepo = departmentRepo;
    }

    public CarDTO createCar(CarDTO carDTO){
        log.info("Adding a new car with details : "+carDTO);
        Department department = departmentRepo.findById(carDTO.getDepartmentId()).orElseThrow(()-> {
            log.error("Department not found with Id: {}",carDTO.getDepartmentId());
            return new EntityNotFoundException("Department not found with Id "+carDTO.getDepartmentId());
        });

        Car convertDTOToEntity = modelMapper.map(carDTO, Car.class);
        convertDTOToEntity.setDepartment(department);

        log.info("Saving car : {}",convertDTOToEntity);
        Car savedCar = carRepo.save(convertDTOToEntity);

        CarDTO convertEntityToDTO = modelMapper.map(savedCar, CarDTO.class);
        convertEntityToDTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        log.info("Car added with Id: {}",savedCar.getCarId());
        return  convertEntityToDTO;
    }

    public CarDTO updateCar(Long id, CarDTO carDTO){
        log.info("fetching department with Id: {}",id);
        Department department = departmentRepo.findById(carDTO.getDepartmentId()).orElseThrow(()-> {
            log.error("Department not found with Id: {}",carDTO.getDepartmentId());
            return new EntityNotFoundException("Department not found with Id: "+carDTO.getDepartmentId());
        });

        Car updateCar = carRepo.findById(id).orElseThrow(()-> {
            log.error("Car not found with Id: {}",id);
            return new EntityNotFoundException("Car not found with Id : "+id);
        });

        log.info("Mapping DTO to Entity: {}",updateCar);
        modelMapper.map(carDTO, updateCar);
        updateCar.setDepartment(department);

        log.info("Updated car details {}",updateCar);
        Car updatedCar = carRepo.save(updateCar);

        CarDTO convertEntityTODTO = modelMapper.map(updatedCar, CarDTO.class);
        convertEntityTODTO.setDepartmentDTO(modelMapper.map(department, DepartmentDTO.class));
        log.info("Car details updated with the Id: {}",convertEntityTODTO);
        return convertEntityTODTO;
    }

    public List<CarDTO> GetAllCars(){
        log.info("Fetching all the cars");
        List<Car> carList = carRepo.findAll();
        List<CarDTO> carDTOList = carList.stream().map(car -> {
            CarDTO convertEntityToDTO = modelMapper.map(car, CarDTO.class);
            Department department = car.getDepartment();
            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
            departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            convertEntityToDTO.setDepartmentDTO(departmentDTO);
            return convertEntityToDTO;
        }).collect(Collectors.toList());
        log.info("Retrieved {} Cars ",carDTOList.size());
        return carDTOList;
    }

    public CarDTO getCarById(Long id){
        log.info("Fetching car with Id : ",id);
        Car carById = carRepo.findById(id).orElseThrow(()-> {
            log.error("Car not found with Id: {}",id);
            return new EntityNotFoundException("Car not found with the Id: "+id);
        });

        CarDTO convertEntityToDTO = modelMapper.map(carById,CarDTO.class);
        Department department = carById.getDepartment();
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
        convertEntityToDTO.setDepartmentDTO(departmentDTO);
        log.info("Car found {}",carById.getCarMake());
        return convertEntityToDTO;
    }

    public void deleteById(Long id){
        log.info("Attempting to delete the car with Id: {}",id);
        Car deleteCar = carRepo.findById(id).orElseThrow(()->{
            log.error("Car not found with Id: {}",id);
            return new EntityNotFoundException("Car not found with Id: "+id);
        });

        carRepo.delete(deleteCar);
        log.info("Car with Id {} deleted successfully",id);
    }
}
