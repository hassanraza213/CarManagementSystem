package com.cms.carManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.carManagementSystem.entity.Ministry;

@Repository
public interface MinistryRepo extends JpaRepository<Ministry, Long> {

}
