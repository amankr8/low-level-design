package service.impl;

import config.GlobalConfig;
import entity.Product;
import exception.InsufficientStockException;
import exception.OptimisticLockException;
import exception.ResourceNotFoundException;
import repository.ProductRepository;
import service.InventoryService;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(int productId) throws ResourceNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateStock(int productId, int changeInQuantity) {
        int maxRetries = GlobalConfig.MAX_RETRY_ATTEMPTS, baseDelay = GlobalConfig.TIMEOUT_MILLISECONDS;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                int newStock = product.getStock() + changeInQuantity;
                if (newStock < 0) {
                    throw new InsufficientStockException("Insufficient stock");
                }
                product.setStock(newStock);
                product.setUpdateDate(new Date());
                productRepository.save(product);
                return;
            } catch (OptimisticLockException ole) {
                if (attempt == maxRetries) {
                    throw new ConcurrentModificationException("Failed to update stock after " + maxRetries + " attempts due to concurrent modifications.");
                }

                // Exponential backoff before retrying
                int delay = Math.min(baseDelay * (1 << (attempt - 1)), 5000);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during backoff delay.");
                }
            }
        }
    }
}
