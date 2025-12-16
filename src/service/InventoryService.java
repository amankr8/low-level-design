package service;

import entity.Product;
import exception.ResourceNotFoundException;

import java.util.List;

public interface InventoryService {

    Product getProductById(int productId) throws ResourceNotFoundException;

    List<Product> getAllProducts();

    void addProduct(Product product);

    void updateStock(int productId, int changeInQuantity);
}
