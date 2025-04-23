//package com.cms.carManagementSystem.seeder;
//
//import com.cms.carManagementSystem.entity.User;
//import com.cms.carManagementSystem.repository.UserRepo;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import net.datafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Slf4j
//@Component
//public class UserSeeder {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private Faker faker;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    @Transactional
//    public void seedUsers() {
//        userRepo.saveAll(
//                IntStream.range(0, 10)
//                        .mapToObj(i -> {
//                            String userName = faker.name().username();
//                            String plainPassword = faker.internet().password();
//                            log.info("Seeding user {} with plain password: {}", userName, plainPassword);
//                            User user = new User();
//                            user.setUserName(userName);
//                            user.setPassword(passwordEncoder.encode(plainPassword)); // Hash the password
//                            user.setDescription(faker.lorem().paragraph(2));
//                            return user;
//                        })
//                        .collect(Collectors.toList())
//        );
//        log.info("Seeded 10 users with hashed passwords.");
//    }
//}