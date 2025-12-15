package service.impl;

import entity.Product;
import repository.ProductRepository;
import service.InventoryService;

import java.util.Date;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(int productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));
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
    public void updateStock(int productId, int changeInQuantity) throws Exception {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new Exception("Product not found"));
            int newStock = product.getStock() + changeInQuantity;
            if (newStock < 0) {
                throw new Exception("Insufficient stock");
            }
            product.setStock(newStock);
            product.setUpdateDate(new Date());
            productRepository.save(product);
        } catch (Exception e) {
            throw new Exception("Error updating stock: " + e.getMessage());
        }
    }
}
