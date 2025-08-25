package com.example.demo.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
@Entity
@Table(name = "stock_history")
@Data
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    
    private Integer adjustmentQuantity;
    
    @Enumerated(EnumType.STRING)
    private AdjustmentType adjustmentType;
    
    private Date timestamp;
    
    public enum AdjustmentType {
        STOCK_IN, STOCK_OUT
    }
}