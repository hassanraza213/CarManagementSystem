package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.repository.MinistryRepo;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class MinistrySeeder {

    @Autowired
    private MinistryRepo ministryRepo;

    @Autowired
    private Faker faker; // Injected singleton

    @PostConstruct
    @Transactional
    public void seedMinistries() {
        ministryRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            Ministry ministry = new Ministry();
                            ministry.setName(faker.company().name() + " Ministry");
                            ministry.setAddress(faker.address().fullAddress());
                            ministry.setDescription(faker.lorem().paragraph(2));
                            return ministry;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 ministries.");
    }
}