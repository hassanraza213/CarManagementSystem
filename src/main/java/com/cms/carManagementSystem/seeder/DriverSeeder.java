package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Driver;
import com.cms.carManagementSystem.repository.DepartmentRepo;
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
public class DriverSeeder {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedDrivers() {
        List<Department> departments = departmentRepo.findAll();
        if (departments.isEmpty()) {
            log.info("No departments; skipping driver seeding.");
            return;
        }

        driverRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Driver driver = new Driver();
                            driver.setDriverName(faker.name().fullName());
                            driver.setLicenseNumber(faker.idNumber().valid());
                            driver.setAvailability(faker.options().option("Available", "Busy", "Off Duty"));
                            driver.setDescription(faker.lorem().paragraph(2));
                            Department department = departmentRepo.findById(departments.get(i % departments.size()).getDepartmentId()).orElseThrow();
                            driver.setDepartment(department);
                            return driver;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 drivers.");
    }
}