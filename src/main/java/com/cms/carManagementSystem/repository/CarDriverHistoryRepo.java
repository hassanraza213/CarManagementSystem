package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.CarDriverHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDriverHistoryRepo extends JpaRepository<CarDriverHistory, Long> {
}
