//package com.cms.carManagementSystem.seeder;
//
//import com.cms.carManagementSystem.entity.Permissions;
//import com.cms.carManagementSystem.repository.PermissionRepo;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@Component
//public class PermissionsSeeder {
//
//    @Autowired
//    private PermissionRepo permissionRepo;
//
//    @PostConstruct
//    @Transactional
//    public void seedPermissions() {
//        List<Permissions> permissions = Arrays.asList(
//                createPermission("CREATE_USER", "Permission to create new users"),
//                createPermission("DELETE_USER", "Permission to delete users"),
//                createPermission("VIEW_REPORTS", "Permission to view reports"),
//                createPermission("MANAGE_ROLES", "Permission to manage roles")
//        );
//
//        permissionRepo.saveAll(permissions);
//        log.info("Seeded {} permissions.", permissions.size());
//    }
//
//    private Permissions createPermission(String permissionName, String description) {
//        Permissions permission = new Permissions();
//        permission.setPermissionName(permissionName);
//        permission.setDescription(description);
//        log.info("Seeding permission: {}", permissionName);
//        return permission;
//    }
//}