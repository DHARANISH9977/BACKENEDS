package com.example.demo.Repository;

import com.example.demo.Entity.Inventory;
import com.example.demo.Entity.Warehouse;
import com.example.demo.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse);
    Optional<Inventory> findByWarehouse(Warehouse warehouse);
}