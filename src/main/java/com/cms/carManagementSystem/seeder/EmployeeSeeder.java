package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Employee;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.EmployeeRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class EmployeeSeeder {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedEmployees() {
        List<Department> departments = departmentRepo.findAll();
        if (departments.isEmpty()) {
            log.info("No departments; skipping employee seeding.");
            return;
        }

        employeeRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Employee employee = new Employee();
                            employee.setName(faker.name().fullName());
                            employee.setEmployeeRank(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10)));
                            employee.setDescription(faker.lorem().paragraph(2));
                            Department department = departmentRepo.findById(departments.get(i % departments.size()).getDepartmentId()).orElseThrow();
                            employee.setDepartment(department);
                            return employee;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 employees.");
    }
}