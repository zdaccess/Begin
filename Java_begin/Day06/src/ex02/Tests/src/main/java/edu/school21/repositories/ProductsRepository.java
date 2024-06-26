package edu.school21.numbers;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    void update(Product product);
    void save(Product product);
    void delete(Long id);
}
