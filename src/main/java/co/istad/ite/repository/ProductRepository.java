package co.istad.ite.repository;

import co.istad.ite.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Category,Integer> {
}
