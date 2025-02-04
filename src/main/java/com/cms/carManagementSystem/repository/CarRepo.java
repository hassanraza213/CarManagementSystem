package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
}
