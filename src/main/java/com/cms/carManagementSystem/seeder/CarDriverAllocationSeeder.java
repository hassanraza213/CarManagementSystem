package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.CarDriverAllocation;
import com.cms.carManagementSystem.entity.Driver;
import com.cms.carManagementSystem.repository.CarDriverAllocationRepo;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.DriverRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class CarDriverAllocationSeeder {

    @Autowired
    private CarDriverAllocationRepo carDriverAllocationRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedCarDriverAllocations() {
        List<Car> cars = carRepo.findAll();
        List<Driver> drivers = driverRepo.findAll();
        if (cars.isEmpty() || drivers.isEmpty()) {
            log.info("No cars or drivers; skipping allocation seeding.");
            return;
        }

        carDriverAllocationRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            CarDriverAllocation allocation = new CarDriverAllocation();
                            allocation.setCar(carRepo.findById(cars.get(i % cars.size()).getCarId()).orElseThrow());
                            allocation.setDriver(driverRepo.findById(drivers.get(i % drivers.size()).getDriverId()).orElseThrow());
                            allocation.setDescription(faker.lorem().paragraph(2));
                            return allocation;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 car-driver allocations.");
    }
}