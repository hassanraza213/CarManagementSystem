package com.cms.carManagementSystem.seeder;

import com.cms.carManagementSystem.entity.AssignPermissionsToRoles;
import com.cms.carManagementSystem.entity.Permissions;
import com.cms.carManagementSystem.entity.Roles;
import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.entity.UserRoles;
import com.cms.carManagementSystem.repository.AssignPermissionsToRolesRepo;
import com.cms.carManagementSystem.repository.PermissionRepo;
import com.cms.carManagementSystem.repository.RolesRepo;
import com.cms.carManagementSystem.repository.UserRepo;
import com.cms.carManagementSystem.repository.UserRolesRepo;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class DataSeeder implements ApplicationRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private UserRolesRepo userRolesRepo;

    @Autowired
    private AssignPermissionsToRolesRepo assignPermissionsToRolesRepo;

    @Autowired
    private Faker faker;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Starting data seeding...");

        // Step 1: Seed Users
        seedUsers();
        log.info("Users seeded.");

        // Step 2: Seed Roles
        seedRoles();
        log.info("Roles seeded.");

        // Step 3: Seed Permissions
        seedPermissions();
        log.info("Permissions seeded.");

        // Step 4: Seed UserRoles
        seedUserRoles();
        log.info("UserRoles seeded.");

        // Step 5: Seed AssignPermissionsToRoles
        seedPermissionToRoles();
        log.info("Permission-to-Role assignments seeded.");

        log.info("Data seeding completed.");
    }

    private void seedUsers() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            List<User> userList = IntStream.range(0, 10)
                    .mapToObj(i -> {
                        String userName = faker.name().username();
                        String plainPassword = faker.internet().password();
                        log.info("Seeding user {} with plain password: {}", userName, plainPassword);
                        User user = new User();
                        user.setUserName(userName);
                        user.setPassword(passwordEncoder.encode(plainPassword));
                        user.setDescription(faker.lorem().paragraph(2));
                        return user;
                    })
                    .collect(Collectors.toList());
            userRepo.saveAll(userList);
            log.info("Seeded {} users with hashed passwords.", userList.size());
        } else {
            log.info("Users already exist, skipping user seeding.");
        }
    }

    private void seedRoles() {
        List<Roles> rolesList = rolesRepo.findAll();
        if (rolesList.isEmpty()) {
            List<Roles> roles = Arrays.asList(
                    createRole("ROLE_ADMIN", "Administrator role with full access"),
                    createRole("ROLE_USER", "Standard user role with limited access"),
                    createRole("ROLE_MANAGER", "Manager role with moderate access")
            );
            rolesRepo.saveAll(roles);
            log.info("Seeded {} roles.", roles.size());
        } else {
            log.info("Roles already exist, skipping role seeding.");
        }
    }

    private Roles createRole(String roleName, String description) {
        Roles role = new Roles();
        role.setRoleName(roleName);
        role.setDescription(description);
        log.info("Seeding role: {}", roleName);
        return role;
    }

    private void seedPermissions() {
        List<Permissions> permissionsList = permissionRepo.findAll();
        if (permissionsList.isEmpty()) {
            List<Permissions> permissions = Arrays.asList(
                    createPermission("CREATE_USER", "Permission to create new users"),
                    createPermission("DELETE_USER", "Permission to delete users"),
                    createPermission("VIEW_REPORTS", "Permission to view reports"),
                    createPermission("MANAGE_ROLES", "Permission to manage roles")
            );
            permissionRepo.saveAll(permissions);
            log.info("Seeded {} permissions.", permissions.size());
        } else {
            log.info("Permissions already exist, skipping permission seeding.");
        }
    }

    private Permissions createPermission(String permissionName, String description) {
        Permissions permission = new Permissions();
        permission.setPermissionName(permissionName);
        permission.setDescription(description);
        log.info("Seeding permission: {}", permissionName);
        return permission;
    }

    private void seedUserRoles() {
        List<UserRoles> userRolesList = userRolesRepo.findAll();
        if (userRolesList.isEmpty()) {
            User adminUser = userRepo.findByUserName("roscoe.hammes").orElseGet(() -> {
                User newAdmin = new User();
                newAdmin.setUserName("roscoe.hammes");
                newAdmin.setPassword(passwordEncoder.encode("bdkhj1o7l3he1p"));
                newAdmin.setDescription("Default admin user");
                return userRepo.save(newAdmin);
            });

            Roles roleAdmin = rolesRepo.findByRoleName("ROLE_ADMIN").orElseThrow(() ->
                    new RuntimeException("Role ROLE_ADMIN not found"));
            Roles roleUser = rolesRepo.findByRoleName("ROLE_USER").orElseThrow(() ->
                    new RuntimeException("Role ROLE_USER not found"));

            List<User> users = userRepo.findAll();
            users.remove(adminUser);

            List<UserRoles> assignments = new ArrayList<>();
            assignments.add(createUserRole(adminUser, roleAdmin, "Admin role for roscoe.hammes"));
            assignments.add(createUserRole(adminUser, roleUser, "User role for roscoe.hammes"));

            for (User user : users) {
                assignments.add(createUserRole(user, roleUser, "User role for " + user.getUserName()));
            }

            userRolesRepo.saveAll(assignments);
            log.info("Seeded {} user-role assignments.", assignments.size());
        } else {
            log.info("UserRoles already exist, skipping user-role seeding.");
        }
    }

    private UserRoles createUserRole(User user, Roles role, String description) {
        UserRoles userRole = new UserRoles();
        userRole.setUser(user);
        userRole.setRoles(role);
        userRole.setDescription(description);
        log.info("Seeding user-role assignment: {} with role {}", user.getUserName(), role.getRoleName());
        return userRole;
    }

    private void seedPermissionToRoles() {
        List<AssignPermissionsToRoles> assignmentsList = assignPermissionsToRolesRepo.findAll();
        if (assignmentsList.isEmpty()) {
            Roles roleAdmin = rolesRepo.findByRoleName("ROLE_ADMIN").orElseThrow(() ->
                    new RuntimeException("Role ROLE_ADMIN not found"));
            Roles roleUser = rolesRepo.findByRoleName("ROLE_USER").orElseThrow(() ->
                    new RuntimeException("Role ROLE_USER not found"));
            Roles roleManager = rolesRepo.findByRoleName("ROLE_MANAGER").orElseThrow(() ->
                    new RuntimeException("Role ROLE_MANAGER not found"));

            Permissions createUser = permissionRepo.findByPermissionName("CREATE_USER").orElseThrow(() ->
                    new RuntimeException("Permission CREATE_USER not found"));
            Permissions deleteUser = permissionRepo.findByPermissionName("DELETE_USER").orElseThrow(() ->
                    new RuntimeException("Permission DELETE_USER not found"));
            Permissions viewReports = permissionRepo.findByPermissionName("VIEW_REPORTS").orElseThrow(() ->
                    new RuntimeException("Permission VIEW_REPORTS not found"));
            Permissions manageRoles = permissionRepo.findByPermissionName("MANAGE_ROLES").orElseThrow(() ->
                    new RuntimeException("Permission MANAGE_ROLES not found"));

            List<AssignPermissionsToRoles> assignments = new ArrayList<>();

            assignments.add(createAssignment(roleAdmin, createUser, "Admin can create users"));
            assignments.add(createAssignment(roleAdmin, deleteUser, "Admin can delete users"));
            assignments.add(createAssignment(roleAdmin, viewReports, "Admin can view reports"));
            assignments.add(createAssignment(roleAdmin, manageRoles, "Admin can manage roles"));

            assignments.add(createAssignment(roleManager, viewReports, "Manager can view reports"));
            assignments.add(createAssignment(roleManager, manageRoles, "Manager can manage roles"));

            assignments.add(createAssignment(roleUser, viewReports, "User can view reports"));

            assignPermissionsToRolesRepo.saveAll(assignments);
            log.info("Seeded {} permission-to-role assignments.", assignments.size());
        } else {
            log.info("Permission-to-Role assignments already exist, skipping seeding.");
        }
    }

    private AssignPermissionsToRoles createAssignment(Roles role, Permissions permission, String description) {
        AssignPermissionsToRoles assignment = new AssignPermissionsToRoles();
        assignment.setRoles(role);
        assignment.setPermissions(permission);
        assignment.setDescription(description);
        log.info("Seeding permission-to-role assignment: {} with permission {}",
                role.getRoleName(), permission.getPermissionName());
        return assignment;
    }
}