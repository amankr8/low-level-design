package repository.impl;

import entity.Product;
import repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepositoryImpl implements ProductRepository {
    private int nextId;
    Map<Integer, Product> productData;

    public ProductRepositoryImpl() {
        nextId = 1;
        productData = new ConcurrentHashMap<>();
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
            product.setProductId(nextId++);
        }
        productData.put(product.getProductId(), product);
    }
}
