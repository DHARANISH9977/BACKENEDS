package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entity.Warehouse;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.StockHistory;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByProductOrderByTimestampDesc(Product product);
    List<StockHistory> findByWarehouseOrderByTimestampDesc(Warehouse warehouse);
    List<StockHistory> findByProductAndWarehouseOrderByTimestampDesc(Product product, Warehouse warehouse);
    List<StockHistory> findAllByOrderByTimestampDesc();
}