package com.cms.carManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.carManagementSystem.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
