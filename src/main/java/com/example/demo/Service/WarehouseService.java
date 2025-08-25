package com.example.demo.Service;

import com.example.demo.Entity.Warehouse;
import com.example.demo.Repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseService {
    
    @Autowired
    private WarehouseRepository warehouseRepository;
    
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
    
    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
    }
    
    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouseRepository.existsByName(warehouse.getName())) {
            throw new RuntimeException("Warehouse with name '" + warehouse.getName() + "' already exists");
        }
        return warehouseRepository.save(warehouse);
    }
    
    @Transactional
    public Warehouse updateWarehouse(Long id, Warehouse warehouseDetails) {
        Warehouse warehouse = getWarehouseById(id);
        
        // Check if name change conflicts with existing warehouse
        if (!warehouse.getName().equals(warehouseDetails.getName()) && 
            warehouseRepository.existsByName(warehouseDetails.getName())) {
            throw new RuntimeException("Warehouse with name '" + warehouseDetails.getName() + "' already exists");
        }
        
        warehouse.setName(warehouseDetails.getName());
        warehouse.setLocation(warehouseDetails.getLocation());
        
        return warehouseRepository.save(warehouse);
    }
    
    @Transactional
    public void deleteWarehouse(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        warehouseRepository.delete(warehouse);
    }
    
    public boolean existsByName(String name) {
        return warehouseRepository.existsByName(name);
    }
}