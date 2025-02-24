package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class CarSeeder {

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedCars() {
        List<Department> departments = departmentRepo.findAll();
        if (departments.isEmpty()) {
            log.info("No departments; skipping car seeding.");
            return;
        }

        carRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Car car = new Car();
                            car.setCarModel(faker.number().numberBetween(2000, 2023)); // Model year
                            car.setCarMake(faker.vehicle().make());
                            car.setCarCondition(faker.options().option("New", "Used", "Damaged"));
                            car.setCarStatus(faker.options().option("Available", "In Use", "Under Repair"));
                            car.setDescription(faker.lorem().paragraph(2));
                            Department department = departmentRepo.findById(departments.get(i % departments.size()).getDepartmentId()).orElseThrow();
                            car.setDepartment(department);
                            return car;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 cars.");
    }
}