package com.cms.carManagementSystem.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(name = "modelMapper")
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Global configuration
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull()) // Map only non-null fields
                .setSkipNullEnabled(true) // Skip null fields in the source object
                .setMatchingStrategy(MatchingStrategies.STRICT) // Use strict matching to avoid unintended mappings
                .setAmbiguityIgnored(true) // Ignore ambiguous mappings
                .setFieldMatchingEnabled(true) // Enable field matching
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // Access private fields

        return modelMapper;
    }
}