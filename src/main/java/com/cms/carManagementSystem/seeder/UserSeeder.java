package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class UserSeeder {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Faker faker;

    @PostConstruct
    @Transactional
    public void seedUsers() {
        userRepo.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> {
                            User user = new User();
                            user.setUserName(faker.name().username());
                            user.setPassword(faker.internet().password());
                            user.setDescription(faker.lorem().paragraph(2));
                            return user;
                        })
                        .collect(Collectors.toList())
        );
        log.info("Seeded 10 users.");
    }
}