package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepo extends JpaRepository<Driver, Long> {
}
