package com.cms.carManagementSystem.repository;

import com.cms.carManagementSystem.entity.AssignPermissionsToRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignPermissionsToRolesRepo extends JpaRepository<AssignPermissionsToRoles, Long> {
    List<AssignPermissionsToRoles> findByRoles_RoleName(String roleName);
}
