package repository;

import entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAllProducts();

    Optional<Product> getProductById(int productId);

    void saveProduct(Product product);
}
