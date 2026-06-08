package co.istad.ite.repository;

import co.istad.ite.domain.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

//    boolean existsByName(@NotBlank(message = "name is required") String name);
}
