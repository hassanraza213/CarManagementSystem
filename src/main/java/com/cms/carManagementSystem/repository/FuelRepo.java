package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepo extends JpaRepository<Fuel, Long> {
}
