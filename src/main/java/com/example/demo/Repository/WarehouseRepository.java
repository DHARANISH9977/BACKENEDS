package com.example.demo.Repository;

import com.example.demo.Entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    boolean existsByName(String name);
    Optional<Warehouse> findByName(String name);
}