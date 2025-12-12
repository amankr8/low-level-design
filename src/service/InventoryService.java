package service;

import entity.Product;

public interface InventoryService {

    void addProduct(Product product);

    void updateStock(int productId, int changeInQuantity) throws Exception;
}
