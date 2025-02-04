package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.EmpCarHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpCarHistoryRepo extends JpaRepository<EmpCarHistory, Long> {
}
