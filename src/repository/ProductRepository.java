package repository;

import entity.Product;
import exception.OptimisticLockException;
import exception.ResourceNotFoundException;

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
        return productData.values()
                .stream()
                .map(Product::copy)
                .toList();
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        Product product = productData.get(productId);
        return product == null ? Optional.empty() : Optional.of(product.copy());
    }

    @Override
    public Product save(Product product) {
        if (product.getProductId() == 0) {
            return insert(product);
        } else {
            return update(product);
        }
    }

    @Override
    public void deleteById(Integer productId) {
        productData.remove(productId);
    }

    private Product insert(Product product) {
        int id = nextId.getAndIncrement();
        Product newProduct = product.copy();
        newProduct.setProductId(id);
        newProduct.setVersion(0);
        productData.put(id, newProduct);
        return newProduct.copy();
    }

    private Product update(Product product) {
        return productData.compute(product.getProductId(), (id, existingProduct) -> {
            if (existingProduct == null) {
                throw new ResourceNotFoundException("Product with ID " + product.getProductId() + " not found.");
            }

            if (existingProduct.getVersion() != product.getVersion()) {
                throw new OptimisticLockException("Product with ID " + product.getProductId() + " has been modified by another transaction.");
            }

            Product update = product.copy();
            update.setVersion(existingProduct.getVersion() + 1);
            return update;
        }).copy();
    }
}
