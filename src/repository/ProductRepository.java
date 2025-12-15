package repository;

import entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductRepository implements BaseRepository<Integer, Product> {
    private final AtomicInteger nextId;
    private final Map<Integer, Product> productData;

    public ProductRepository() {
        nextId = new AtomicInteger(1);
        productData = new ConcurrentHashMap<>();
    }

    @Override
    public List<Product> findAll() {
        return productData.values().stream().toList();
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return Optional.ofNullable(productData.get(productId));
    }

    @Override
    public Product save(Product product) {
        if (product.getProductId() == 0) {
            int id = nextId.getAndIncrement();
            product.setProductId(id);
        }
        productData.put(product.getProductId(), product);
        return product;
    }
}
