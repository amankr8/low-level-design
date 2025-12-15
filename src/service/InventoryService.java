package service;

import entity.Product;

import java.util.List;

public interface InventoryService {

    Product getProductById(int productId) throws Exception;

    List<Product> getAllProducts();

    void addProduct(Product product);

    void updateStock(int productId, int changeInQuantity) throws Exception;
}
