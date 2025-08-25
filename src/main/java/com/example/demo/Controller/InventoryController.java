package com.example.demo.Controller;

import com.example.demo.Entity.Inventory;
import com.example.demo.Entity.StockHistory;
import com.example.demo.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @PostMapping("/adjust")
    public Inventory adjustStock(@RequestBody Map<String, Object> request) {
        Long warehouseId = Long.parseLong(request.get("warehouse_id").toString());
        int quantity = Integer.parseInt(request.get("adjustment_quantity").toString());
        String adjustmentType = request.get("adjustment_type").toString();
        
        StockHistory.AdjustmentType type = StockHistory.AdjustmentType.valueOf(adjustmentType);
        return inventoryService.adjustStock(warehouseId, quantity, type);
    }

    @GetMapping("/history")
    public List<StockHistory> getStockHistory(@RequestParam(required = false) Long warehouseId) {
        return inventoryService.getStockHistory(warehouseId);
    }
    
   
    @GetMapping("/history/dummy-pagination")
    public Page<StockHistory> getDummyPagination(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.getDummyPagination(warehouseId, page, size);
    }
}