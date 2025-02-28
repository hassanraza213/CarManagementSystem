package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Car;
import com.cms.carManagementSystem.entity.CarDriverAllocation;
import com.cms.carManagementSystem.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDriverAllocationRepo extends JpaRepository<CarDriverAllocation, Long> {
    CarDriverAllocation findByCarAndDriver(Car car, Driver driver);
}
