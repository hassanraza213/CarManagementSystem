package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepo extends JpaRepository<UserRoles, Long> {
}
