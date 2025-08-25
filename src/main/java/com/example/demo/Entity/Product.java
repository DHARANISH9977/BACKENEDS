package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String sku;
    
    private String description;
    
    private Integer minStockLevel;
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @JsonIgnoreProperties("products")
    private Warehouse warehouse;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties("products") // Prevent infinite recursion
    private Supplier supplier;
}