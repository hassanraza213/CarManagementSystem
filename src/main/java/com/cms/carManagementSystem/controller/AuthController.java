package com.cms.carManagementSystem.controller;

import com.cms.carManagementSystem.dto.UserDTO;
import com.cms.carManagementSystem.service.UserService;
import com.cms.carManagementSystem.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Data
    public static class LoginRequest{
        private String userName;
        private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        log.info("Login attempt for username : {} ",loginRequest.getUserName());
        try{
            UserDTO userDTO = userService.login(loginRequest.getUserName(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(userDTO.getUserName());
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
