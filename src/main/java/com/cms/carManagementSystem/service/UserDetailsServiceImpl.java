package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.entity.User;
import com.cms.carManagementSystem.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("Loading user details for username: {}", userName);
        User user = userRepo.findByUserName(userName).orElseThrow(() -> {
            log.error("User not found with username: {}", userName);
            throw new UsernameNotFoundException("User not found with username: " + userName);
        });

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(java.util.Collections.emptyList())
                .build();
    }
}
