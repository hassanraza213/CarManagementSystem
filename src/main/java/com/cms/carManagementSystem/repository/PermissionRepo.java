package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepo extends JpaRepository<Permissions, Long> {
    Optional<Permissions> findByPermissionName(String permissionName);
}
