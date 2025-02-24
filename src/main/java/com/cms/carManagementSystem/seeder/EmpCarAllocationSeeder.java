package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.EmpCarAllocation;
import com.cms.carManagementSystem.entity.Employee;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.EmpCarAllocationRepo;
import com.cms.carManagementSystem.repository.EmployeeRepo;
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
public class EmpCarAllocationSeeder {

    @Autowired
    private EmpCarAllocationRepo empCarAllocationRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedEmpCarAllocations() {
        List<Car> cars = carRepo.findAll();
        List<Employee> employees = employeeRepo.findAll();
        if (cars.isEmpty() || employees.isEmpty()) {
            log.info("No cars or employees; skipping allocation seeding.");
            return;
        }

        empCarAllocationRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            EmpCarAllocation allocation = new EmpCarAllocation();
                            allocation.setCar(carRepo.findById(cars.get(i % cars.size()).getCarId()).orElseThrow());
                            allocation.setEmployee(employeeRepo.findById(employees.get(i % employees.size()).getEmployeeId()).orElseThrow());
                            allocation.setDescription(faker.lorem().paragraph(2));
                            return allocation;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 car allocations.");
    }
}