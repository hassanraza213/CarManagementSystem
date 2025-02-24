package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.CarDriverHistory;
import com.cms.carManagementSystem.entity.Driver;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.CarDriverHistoryRepo;
import com.cms.carManagementSystem.repository.DriverRepo;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class CarDriverHistorySeeder {

    @Autowired
    private CarDriverHistoryRepo carDriverHistoryRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedCarDriverHistory() {
        List<Car> cars = carRepo.findAll();
        List<Driver> drivers = driverRepo.findAll();
        if (cars.isEmpty() || drivers.isEmpty()) {
            log.info("No cars or drivers; skipping history seeding.");
            return;
        }

        carDriverHistoryRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            CarDriverHistory history = new CarDriverHistory();
                            history.setStartDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)));
                            history.setEndDate(LocalDate.now().plusDays(faker.number().numberBetween(1, 30)));
                            history.setCar(carRepo.findById(cars.get(i % cars.size()).getCarId()).orElseThrow());
                            history.setDriver(driverRepo.findById(drivers.get(i % drivers.size()).getDriverId()).orElseThrow());
                            history.setDescription(faker.lorem().paragraph(2));
                            return history;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 car-driver history records.");
    }
}