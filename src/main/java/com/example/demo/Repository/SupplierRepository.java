package com.example.demo.Repository;
import com.example.demo.Entity.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByName(String name);
}