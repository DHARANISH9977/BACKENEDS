package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.config.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            User user = null;
            boolean isDemoAccount = false;
            
          
            if ("admin@example.com".equals(email)) {
                user = new User();
                user.setId(1L);
                user.setEmail(email);
                user.setName("Admin User");
                user.setRole(User.Role.ADMIN);
                user.setPasswordHash(passwordEncoder.encode("admin123")); 
                isDemoAccount = true;
            } 
            else if ("manager@example.com".equals(email)) {
                user = new User();
                user.setId(2L);
                user.setEmail(email);
                user.setName("Manager User");
                user.setRole(User.Role.MANAGER);
                user.setPasswordHash(passwordEncoder.encode("manager123")); 
                isDemoAccount = true;
            }
            else if ("employee@example.com".equals(email)) {
                user = new User();
                user.setId(3L);
                user.setEmail(email);
                user.setName("Employee User");
                user.setRole(User.Role.EMPLOYEE);
                user.setPasswordHash(passwordEncoder.encode("employee123")); 
                isDemoAccount = true;
            }
            
            else {
                user = userService.getUserByEmail(email);
                if (user != null) {
                
                    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
                        response.put("error", "Invalid password");
                        return response;
                    }
                }
            }
            
            if (user != null) {
                
                if (isDemoAccount && !passwordEncoder.matches(password, user.getPasswordHash())) {
                    response.put("error", "Invalid password for demo account");
                    return response;
                }
                
                // Generate JWT token
                String token = jwtTokenProvider.generateToken(
                    user.getEmail(), 
                    user.getRole().name(), 
                    user.getId()
                );
                
                response.put("token", token);
                response.put("user", Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "name", user.getName(),
                    "role", user.getRole().name()
                ));
            } else {
                response.put("error", "User not found");
            }
            
        } catch (Exception e) {
            response.put("error", "Authentication failed: " + e.getMessage());
        }
        
        return response;
    }
}