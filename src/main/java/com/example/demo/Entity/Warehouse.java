package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    private String location;
    
    // Remove the products relationship for now to avoid circular references
    // @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    // @JsonIgnoreProperties("warehouse")
    // private List<Product> products = new ArrayList<>();
}