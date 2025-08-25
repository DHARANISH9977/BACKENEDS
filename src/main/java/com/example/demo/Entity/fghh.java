package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "warehouses")
@Data
public class fghh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    private String location;
}