package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Ministry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinistryRepo extends JpaRepository<Ministry, Long> {

}
