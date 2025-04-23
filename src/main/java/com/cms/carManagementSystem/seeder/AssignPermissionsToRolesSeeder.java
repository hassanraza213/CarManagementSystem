//package com.cms.carManagementSystem.seeder;
//
//import com.cms.carManagementSystem.entity.AssignPermissionsToRoles;
//import com.cms.carManagementSystem.entity.Permissions;
//import com.cms.carManagementSystem.entity.Roles;
//import com.cms.carManagementSystem.repository.AssignPermissionsToRolesRepo;
//import com.cms.carManagementSystem.repository.PermissionRepo;
//import com.cms.carManagementSystem.repository.RolesRepo;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//public class AssignPermissionsToRolesSeeder {
//
//    @Autowired
//    private AssignPermissionsToRolesRepo assignPermissionsToRolesRepo;
//
//    @Autowired
//    private RolesRepo rolesRepo;
//
//    @Autowired
//    private PermissionRepo permissionRepo;
//
//    @PostConstruct
//    @Transactional
//    @DependsOn({"rolesSeeder", "permissionsSeeder"})
//    public void seedPermissionToRoles() {
//        // Fetch roles
//        Roles roleAdmin = rolesRepo.findByRoleName("ROLE_ADMIN").orElseThrow(() ->
//                new RuntimeException("Role ROLE_ADMIN not found"));
//        Roles roleUser = rolesRepo.findByRoleName("ROLE_USER").orElseThrow(() ->
//                new RuntimeException("Role ROLE_USER not found"));
//        Roles roleManager = rolesRepo.findByRoleName("ROLE_MANAGER").orElseThrow(() ->
//                new RuntimeException("Role ROLE_MANAGER not found"));
//
//        List<Permissions> allPermissions = permissionRepo.findAll();
//        log.info("Available permissions in database: {}", allPermissions.stream().map(Permissions::getPermissionName).toList());
//
//        // Fetch permissions
//        Permissions createUser = permissionRepo.findByPermissionName("CREATE_USER").orElseThrow(() ->
//                new RuntimeException("Permission CREATE_USER not found"));
//        Permissions deleteUser = permissionRepo.findByPermissionName("DELETE_USER").orElseThrow(() ->
//                new RuntimeException("Permission DELETE_USER not found"));
//        Permissions viewReports = permissionRepo.findByPermissionName("VIEW_REPORTS").orElseThrow(() ->
//                new RuntimeException("Permission VIEW_REPORTS not found"));
//        Permissions manageRoles = permissionRepo.findByPermissionName("MANAGE_ROLES").orElseThrow(() ->
//                new RuntimeException("Permission MANAGE_ROLES not found"));
//
//        // Assign permissions to roles
//        List<AssignPermissionsToRoles> assignments = new ArrayList<>();
//
//        // ROLE_ADMIN gets all permissions
//        assignments.add(createAssignment(roleAdmin, createUser, "Admin can create users"));
//        assignments.add(createAssignment(roleAdmin, deleteUser, "Admin can delete users"));
//        assignments.add(createAssignment(roleAdmin, viewReports, "Admin can view reports"));
//        assignments.add(createAssignment(roleAdmin, manageRoles, "Admin can manage roles"));
//
//        // ROLE_MANAGER gets some permissions
//        assignments.add(createAssignment(roleManager, viewReports, "Manager can view reports"));
//        assignments.add(createAssignment(roleManager, manageRoles, "Manager can manage roles"));
//
//        // ROLE_USER gets limited permissions
//        assignments.add(createAssignment(roleUser, viewReports, "User can view reports"));
//
//        assignPermissionsToRolesRepo.saveAll(assignments);
//        log.info("Seeded {} permission-to-role assignments.", assignments.size());
//    }
//
//    private AssignPermissionsToRoles createAssignment(Roles role, Permissions permission, String description) {
//        AssignPermissionsToRoles assignment = new AssignPermissionsToRoles();
//        assignment.setRoles(role);
//        assignment.setPermissions(permission);
//        assignment.setDescription(description);
//        log.info("Seeding permission-to-role assignment: {} with permission {}",
//                role.getRoleName(), permission.getPermissionName());
//        return assignment;
//    }
//}
