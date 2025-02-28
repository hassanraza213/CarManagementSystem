package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.Fuel;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.FuelRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class FuelSeeder {

    @Autowired
    private FuelRepo fuelRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedFuel() {
        List<Car> cars = carRepo.findAll();
        if (cars.isEmpty()) {
            log.info("No cars; skipping fuel seeding.");
            return;
        }

        fuelRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Fuel fuel = new Fuel();
                            fuel.setFuelType(faker.options().option("Gasoline", "Diesel", "Electric"));
                            fuel.setFuelQuantity(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 50)));
                            fuel.setDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)));
                            fuel.setCar(carRepo.findById(cars.get(i % cars.size()).getCarId()).orElseThrow());
                            fuel.setDescription(faker.lorem().paragraph(2));
                            return fuel;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 fuel records.");
    }
}