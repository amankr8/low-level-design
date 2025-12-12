package repository.impl;

import entity.Product;
import repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    Map<Integer, Product> productData;

    public ProductRepositoryImpl() {
        productData = new HashMap<>();
    }

    @Override
    public List<Product> getAllProducts() {
        return productData.values().stream().toList();
    }

    @Override
    public Optional<Product> getProductById(int productId) {
        return Optional.ofNullable(productData.get(productId));
    }

    @Override
    public void saveProduct(Product product) {
        if (product.getProductId() == 0) {
            int productId = generateUniqueProductId();
            product.setProductId(productId);
        }
        productData.put(product.getProductId(), product);
    }

    private int generateUniqueProductId() {
        int id = 1;
        while (productData.containsKey(id)) {
            id++;
        }
        return id;
    }
}
