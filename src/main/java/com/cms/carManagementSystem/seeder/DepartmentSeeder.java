package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.MinistryRepo;
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
public class DepartmentSeeder {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private MinistryRepo ministryRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedDepartments() {
        List<Ministry> ministries = ministryRepo.findAll();
        if (ministries.isEmpty()) {
            log.info("No ministries; skipping department seeding.");
            return;
        }

        departmentRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Department department = new Department();
                            department.setName(faker.company().industry() + " Department");
                            department.setDescription(faker.lorem().paragraph(2));
                            Ministry ministry = ministryRepo.findById(ministries.get(i % ministries.size()).getMinistryId()).orElseThrow();
                            department.setMinistry(ministry);
                            return department;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 departments.");
    }
}