//package com.cms.carManagementSystem.seeder;
//
//import com.cms.carManagementSystem.entity.Roles;
//import com.cms.carManagementSystem.entity.User;
//import com.cms.carManagementSystem.entity.UserRoles;
//import com.cms.carManagementSystem.repository.RolesRepo;
//import com.cms.carManagementSystem.repository.UserRepo;
//import com.cms.carManagementSystem.repository.UserRolesRepo;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//public class UserRolesSeeder {
//
//    @Autowired
//    private UserRolesRepo userRolesRepo;
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private RolesRepo rolesRepo;
//
//    @PostConstruct
//    @Transactional
//    public void seedUserRoles() {
//        // Fetch the first user (e.g., roscoe.hammes) and roles
//        User adminUser = userRepo.findByUserName("roscoe.hammes").orElseThrow(() ->
//                new RuntimeException("Admin user roscoe.hammes not found"));
//        Roles roleAdmin = rolesRepo.findByRoleName("ROLE_ADMIN").orElseThrow(() ->
//                new RuntimeException("Role ROLE_ADMIN not found"));
//        Roles roleUser = rolesRepo.findByRoleName("ROLE_USER").orElseThrow(() ->
//                new RuntimeException("Role ROLE_USER not found"));
//
//        // Fetch other users to assign ROLE_USER
//        List<User> users = userRepo.findAll();
//        users.remove(adminUser); // Exclude admin user
//
//        List<UserRoles> userRolesList = new ArrayList<>();
//
//        // Assign ROLE_ADMIN and ROLE_USER to roscoe.hammes
//        userRolesList.add(createUserRole(adminUser, roleAdmin, "Admin role for roscoe.hammes"));
//        userRolesList.add(createUserRole(adminUser, roleUser, "User role for roscoe.hammes"));
//
//        // Assign ROLE_USER to other users
//        for (User user : users) {
//            userRolesList.add(createUserRole(user, roleUser, "User role for " + user.getUserName()));
//        }
//
//        userRolesRepo.saveAll(userRolesList);
//        log.info("Seeded {} user-role assignments.", userRolesList.size());
//    }
//
//    private UserRoles createUserRole(User user, Roles role, String description) {
//        UserRoles userRole = new UserRoles();
//        userRole.setUser(user);
//        userRole.setRoles(role);
//        userRole.setDescription(description);
//        log.info("Seeding user-role assignment: {} with role {}", user.getUserName(), role.getRoleName());
//        return userRole;
//    }
//}
