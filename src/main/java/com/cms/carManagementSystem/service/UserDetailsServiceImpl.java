package com.cms.carManagementSystem.service;


import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserRolesService userRolesService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("Loading user details for username: {}", userName);
        User user = userRepo.findByUserName(userName).orElseThrow(() -> {
            log.error("User not found with username: {}", userName);
            throw new UsernameNotFoundException("User not found with username: " + userName);
        });

        List<String> roleNames = userRolesService.getRolesByUserId(user.getUserId());

        Set<String> permissions = new HashSet<>();
        for (String roleName : roleNames) {
            List<String> rolePermissions = userRolesService.getPermissionsByRoleName(roleName);
            permissions.addAll(rolePermissions);
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(roleNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        authorities.addAll(permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }
}
