package service.impl;

import entity.Product;
import repository.ProductRepository;
import service.InventoryService;

import java.util.Date;

public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.saveProduct(product);
    }

    @Override
    public void updateStock(int productId, int changeInQuantity) throws Exception {
        try {
            Product product = productRepository.getProductById(productId)
                    .orElseThrow(() -> new Exception("Product not found"));
            int newStock = product.getStock() + changeInQuantity;
            if (newStock < 0) {
                throw new Exception("Insufficient stock");
            }
            product.setStock(newStock);
            product.setUpdateDate(new Date());
            productRepository.saveProduct(product);
        } catch (Exception e) {
            throw new Exception("Error updating stock: " + e.getMessage());
        }
    }
}
