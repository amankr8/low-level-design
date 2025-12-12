package service.impl;

import entity.Product;
import repository.impl.ProductRepositoryImpl;
import service.InventoryService;

public class InventoryServiceImpl implements InventoryService {

    private final ProductRepositoryImpl productRepositoryImpl;

    public InventoryServiceImpl(ProductRepositoryImpl productRepositoryImpl) {
        this.productRepositoryImpl = productRepositoryImpl;
    }

    @Override
    public void addProduct(Product product) {
        productRepositoryImpl.saveProduct(product);
    }

    @Override
    public void updateStock(int productId, int changeInQuantity) throws Exception {
        try {
            Product product = productRepositoryImpl.getProductById(productId)
                    .orElseThrow(() -> new Exception("Product not found"));
            int newStock = product.getStock() + changeInQuantity;
            if (newStock < 0) {
                throw new Exception("Insufficient stock");
            }
            product.setStock(newStock);
            productRepositoryImpl.saveProduct(product);
        } catch (Exception e) {
            throw new Exception("Error updating stock: " + e.getMessage());
        }
    }
}
