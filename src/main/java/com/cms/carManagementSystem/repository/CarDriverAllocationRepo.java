package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.CarDriverAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDriverAllocationRepo extends JpaRepository<CarDriverAllocation, Long> {
}
