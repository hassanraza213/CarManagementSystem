package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.Maintenance;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.MaintenanceRepo;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class MaintenanceSeeder {

    @Autowired
    private MaintenanceRepo maintenanceRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedMaintenance() {
        List<Car> cars = carRepo.findAll();
        if (cars.isEmpty()) {
            log.info("No cars; skipping maintenance seeding.");
            return;
        }

        maintenanceRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Maintenance maintenance = new Maintenance();
                            maintenance.setMaintenanceDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)));
                            maintenance.setMaintenanceCost(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 1000)));
                            maintenance.setMaintenance_description(faker.lorem().sentence());
                            maintenance.setCar(carRepo.findById(cars.get(i % cars.size()).getCarId()).orElseThrow());
                            maintenance.setDescription(faker.lorem().paragraph(2));
                            return maintenance;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 maintenance records.");
    }
}