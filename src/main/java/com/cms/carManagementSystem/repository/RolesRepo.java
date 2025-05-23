package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(String roleName);
}
