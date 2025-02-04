package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.CarDTO;
import com.cms.carManagementSystem.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@Slf4j
public class CarController {

    @Autowired
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO carDTO){
        log.info("Adding car {}",carDTO);
        CarDTO createCar = carService.createCar(carDTO);
        log.info("Car added successfully {}",createCar);
        return new ResponseEntity<>(createCar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long id, @Valid @RequestBody CarDTO carDTO){
        log.info("Fetching car with Id: {}",id);
        try{
            CarDTO updateCar = carService.updateCar(id,carDTO);
            log.info("Car updated successfully {}",updateCar);
            return ResponseEntity.ok(updateCar);
        } catch(EntityNotFoundException e){
            log.error("Car not found with Id: {}",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id){
        log.info("Fetching car with Id: {}",id);
        try{
            CarDTO carById = carService.getCarById(id);
            log.info("Car with Id {} fetched successfully ",id);
            return ResponseEntity.ok(carById);
        }catch (EntityNotFoundException e){
            log.error("Car not found with Id: {}",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<CarDTO> getAllCars(){
        log.info("Fetching all cars");
        return carService.GetAllCars();
    }

    public ResponseEntity<String> deleteCar(@PathVariable Long id){
        log.info("Attempting to delete a car with Id: {}",id);
        try{
            carService.deleteById(id);
            log.info("Car deleted successfully with Id: {}",id);
            return ResponseEntity.ok("Car deleted Successfully with Id: "+id);
        }catch (EntityNotFoundException e){
            log.error("Car not found with Id: {}",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car with this Id "+ id +" not found");
        }
    }
}
