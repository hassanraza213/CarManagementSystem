package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepo extends JpaRepository<UserRoles, Long> {
    List<UserRoles> findByUser_UserId(Long userId);
}