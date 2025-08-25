package com.example.demo.exception;
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException() {
        super();
    }

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientStockException(Throwable cause) {
        super(cause);
    }

    // Optional: Additional constructors with product/warehouse details
    public InsufficientStockException(Long productId, Long warehouseId, int available, int requested) {
        super(String.format(
            "Insufficient stock for product %d in warehouse %d. Available: %d, Requested: %d",
            productId, warehouseId, available, requested
        ));
    }

    public InsufficientStockException(String productName, String warehouseName, int available, int requested) {
        super(String.format(
            "Insufficient stock for product '%s' in warehouse '%s'. Available: %d, Requested: %d",
            productName, warehouseName, available, requested
        ));
    }
}