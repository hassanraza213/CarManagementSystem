//package com.cms.carManagementSystem.seeder;
//
//import com.cms.carManagementSystem.entity.Roles;
//import com.cms.carManagementSystem.repository.RolesRepo;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//public class RolesSeeder {
//
//    @Autowired
//    private RolesRepo rolesRepo;
//
//    @PostConstruct
//    @Transactional
//    public void seedRoles() {
//        List<Roles> roles = Arrays.asList(
//                createRole("ROLE_ADMIN", "Administrator role with full access"),
//                createRole("ROLE_USER", "Standard user role with limited access"),
//                createRole("ROLE_MANAGER", "Manager role with moderate access")
//        );
//
//        List<Roles> rolesToSave = roles.stream()
//                .filter(role -> rolesRepo.findByRoleName(role.getRoleName()).isEmpty())
//                .collect(Collectors.toList());
//
//        if (!rolesToSave.isEmpty()) {
//            rolesRepo.saveAll(rolesToSave);
//            log.info("Seeded {} new roles.", rolesToSave.size());
//        } else {
//            log.info("All roles already exist, skipping seeding.");
//        }
//    }
//
//    private Roles createRole(String roleName, String description) {
//        Roles role = new Roles();
//        role.setRoleName(roleName);
//        role.setDescription(description);
//        log.info("Seeding role: {}", roleName);
//        return role;
//    }
//}