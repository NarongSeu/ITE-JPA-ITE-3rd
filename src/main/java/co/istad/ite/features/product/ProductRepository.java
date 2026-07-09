package co.istad.ite.features.product;

import co.istad.ite.features.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Optional<Product> findByCode(String code);
    boolean existsByName(String name);

    List<Product> id(Integer id);
}