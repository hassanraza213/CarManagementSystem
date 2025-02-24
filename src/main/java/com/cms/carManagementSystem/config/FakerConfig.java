package com.cms.carManagementSystem.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FakerConfig {

    @Bean
    @Scope("singleton")
    public Faker faker() {
        return new Faker();
    }
}