package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.EmpCarAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpCarAllocationRepo extends JpaRepository<EmpCarAllocation, Long> {
}
