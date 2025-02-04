package com.cms.carManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.carManagementSystem.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

}
