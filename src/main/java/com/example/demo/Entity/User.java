package com.example.demo.Entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "users")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "password_hash", nullable = false) // Map to password_hash column
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Version
    @Column(name = "version")
    private Long version;
    
    public enum Role {
        ADMIN, MANAGER, EMPLOYEE
    }
}