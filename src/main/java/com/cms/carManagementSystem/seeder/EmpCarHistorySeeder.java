package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.EmpCarHistory;
import com.cms.carManagementSystem.entity.Employee;
import com.cms.carManagementSystem.repository.CarRepo;
import com.cms.carManagementSystem.repository.EmpCarHistoryRepo;
import com.cms.carManagementSystem.repository.EmployeeRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class EmpCarHistorySeeder {

    @Autowired
    private EmpCarHistoryRepo empCarHistoryRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedEmpCarHistory() {
        List<Car> cars = carRepo.findAll();
        List<Employee> employees = employeeRepo.findAll();
        if (cars.isEmpty() || employees.isEmpty()) {
            log.info("No cars or employees; skipping history seeding.");
            return;
        }

        empCarHistoryRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            EmpCarHistory history = new EmpCarHistory();
                            history.setStartDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)));
                            history.setEndDate(LocalDate.now().plusDays(faker.number().numberBetween(1, 30)));
                            history.setCar(carRepo.findById(cars.get(i % cars.size()).getCarId()).orElseThrow());
                            history.setEmployee(employeeRepo.findById(employees.get(i % employees.size()).getEmployeeId()).orElseThrow());
                            history.setDescription(faker.lorem().paragraph(2));
                            return history;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 car history records.");
    }
}