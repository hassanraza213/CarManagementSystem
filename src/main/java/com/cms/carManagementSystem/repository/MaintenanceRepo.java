package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepo extends JpaRepository<Maintenance, Long> {
}
