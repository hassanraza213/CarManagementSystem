package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Budget;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.repository.BudgetRepo;
import com.cms.carManagementSystem.repository.DepartmentRepo;
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
public class BudgetSeeder {

    @Autowired
    private BudgetRepo budgetRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedBudgets() {
        List<Department> departments = departmentRepo.findAll();
        if (departments.isEmpty()) {
            log.info("No departments; skipping budget seeding.");
            return;
        }

        budgetRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Budget budget = new Budget();
                            budget.setNewCarBudget(BigDecimal.valueOf(faker.number().randomDouble(2, 10000, 50000)));
                            budget.setMaintenanceBudget(BigDecimal.valueOf(faker.number().randomDouble(2, 5000, 20000)));
                            budget.setDepartment(departmentRepo.findById(departments.get(i % departments.size()).getDepartmentId()).orElseThrow());
                            budget.setDescription(faker.lorem().paragraph(2));
                            return budget;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 budgets.");
    }
}