package com.example.demo.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.Warehouse;
import com.example.demo.Entity.Supplier;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.WarehouseRepository;
import com.example.demo.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private WarehouseRepository warehouseRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    @Transactional
    public Product createProduct(Product product) {
        // Check if SKU already exists
        if (productRepository.existsBySku(product.getSku())) {
            throw new RuntimeException("Product with SKU " + product.getSku() + " already exists");
        }
        
        // Handle warehouse association
        if (product.getWarehouse() != null && product.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepository.findById(product.getWarehouse().getId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + product.getWarehouse().getId()));
            product.setWarehouse(warehouse);
        }
        
        // Handle supplier association
        if (product.getSupplier() != null && product.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(product.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + product.getSupplier().getId()));
            product.setSupplier(supplier);
        }
        
        return productRepository.save(product);
    }
    
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        
        product.setName(productDetails.getName());
        product.setSku(productDetails.getSku());
        product.setDescription(productDetails.getDescription());
        product.setMinStockLevel(productDetails.getMinStockLevel());
        
        // Handle warehouse update
        if (productDetails.getWarehouse() != null && productDetails.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepository.findById(productDetails.getWarehouse().getId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + productDetails.getWarehouse().getId()));
            product.setWarehouse(warehouse);
        } else {
            product.setWarehouse(null);
        }
        
        // Handle supplier update
        if (productDetails.getSupplier() != null && productDetails.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(productDetails.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + productDetails.getSupplier().getId()));
            product.setSupplier(supplier);
        } else {
            product.setSupplier(null);
        }
        
        return productRepository.save(product);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
    
    public List<Product> getProductsByWarehouse(Long warehouseId) {
        return productRepository.findByWarehouseId(warehouseId);
    }
    
    public List<Product> getProductsBySupplier(Long supplierId) {
        return productRepository.findBySupplierId(supplierId);
    }
}