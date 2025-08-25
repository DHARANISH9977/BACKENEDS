package com.example.demo;

import lombok.Data;
import java.util.List;

@Data
public class ProductRequest {
    private String name;
    private String sku;
    private String description;
    private Integer minStockLevel;
    private Long supplierId;
    private List<Long> warehouseIds;
}