package co.istad.ite.features.product;

import co.istad.ite.features.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    boolean existsByName(String name);
}