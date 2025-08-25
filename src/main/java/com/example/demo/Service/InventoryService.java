package com.example.demo.Service;

import com.example.demo.Entity.*;
import com.example.demo.exception.*;
import com.example.demo.Repository.InventoryRepository;
import com.example.demo.Repository.StockHistoryRepository;
import com.example.demo.Repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public InventoryService(InventoryRepository inventoryRepository,
                            WarehouseRepository warehouseRepository,
                            StockHistoryRepository stockHistoryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory record not found"));
    }
    
    @Transactional
    public Inventory createInventoryRecord(Long warehouseId, int initialStockLevel) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
            .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        
        // Check if inventory already exists for this warehouse
        Optional<Inventory> existingInventory = inventoryRepository.findByWarehouse(warehouse);
        if (existingInventory.isPresent()) {
            throw new RuntimeException("Inventory record already exists for this warehouse");
        }
        
        // Create new inventory record
        Inventory inventory = new Inventory();
        inventory.setWarehouse(warehouse);
        inventory.setStockLevel(initialStockLevel);
        
        return inventoryRepository.save(inventory);
    }
    
    @Transactional
    public Inventory adjustStock(Long warehouseId, int quantity, StockHistory.AdjustmentType adjustmentType) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
            .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        
        // Get existing inventory for this warehouse or create a new one
        Inventory inventory = inventoryRepository.findByWarehouse(warehouse)
            .orElseGet(() -> {
                // Create new inventory record with 0 stock
                Inventory newInventory = new Inventory();
                newInventory.setWarehouse(warehouse);
                newInventory.setStockLevel(0);
                return inventoryRepository.save(newInventory);
            });
        
        int newStockLevel;
        if (adjustmentType == StockHistory.AdjustmentType.STOCK_IN) {
            newStockLevel = inventory.getStockLevel() + quantity;
        } else {
            // STOCK_OUT case: prevent negative stock
            if (inventory.getStockLevel() < quantity) {
                throw new RuntimeException("Stock cannot go negative");
            }
            newStockLevel = inventory.getStockLevel() - quantity;
        }
        
        inventory.setStockLevel(newStockLevel);
        inventoryRepository.save(inventory);
        
        // Record stock adjustment in history
        StockHistory history = new StockHistory();
        history.setWarehouse(warehouse);
        history.setAdjustmentQuantity(quantity);
        history.setAdjustmentType(adjustmentType);
        history.setTimestamp(new Date());
        stockHistoryRepository.save(history);
        
        return inventory;
    }
    
    public List<StockHistory> getStockHistory(Long warehouseId) {
        if (warehouseId != null) {
            Warehouse warehouse = warehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new NotFoundException("Warehouse not found"));
            return stockHistoryRepository.findByWarehouseOrderByTimestampDesc(warehouse);
        }
        return stockHistoryRepository.findAllByOrderByTimestampDesc();
    }
    
   
    public Page<StockHistory> getDummyPagination(Long warehouseId, int page, int size) { 
        Pageable pageable = PageRequest.of(page, size);
        List<StockHistory> result = getStockHistory(warehouseId);
        

        int total = result.size();
        int start = Math.min((int) pageable.getOffset(), total);
        int end = Math.min((start + pageable.getPageSize()), total);
        
        
        return new PageImpl<>(result, pageable, total);
    }
}