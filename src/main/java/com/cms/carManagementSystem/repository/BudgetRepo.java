package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepo extends JpaRepository<Budget, Long> {
}
