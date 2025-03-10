package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.UserDTO;
import com.cms.carManagementSystem.service.UserDetailsServiceImpl;
import com.cms.carManagementSystem.service.UserService;
import com.cms.carManagementSystem.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Data
    public static class LoginRequest {
        private String userName;
        private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        log.info("Login attempt for username: {}", loginRequest.getUserName());
        try {
            UserDTO userDTO = userService.login(loginRequest.getUserName(), loginRequest.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUserName());
            List<String> authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String token = jwtUtil.generateToken(userDTO.getUserName(), authorities);
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge((int) (jwtUtil.getExpirationTime() / 1000));
            httpServletResponse.addCookie(cookie);
            log.info("Login successful, JWT cookie set for username: {}", loginRequest.getUserName());
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            log.error("Login failed for username: {}, Error: {}", loginRequest.getUserName(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}