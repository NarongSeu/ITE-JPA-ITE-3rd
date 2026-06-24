package co.istad.ite.features.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    Page<Category> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    boolean existsByName(String name);


    List<Category> findAllByIsDeletedAndParentCategory(boolean isDeleted, Category parentCategory);

    Optional<Category> findByIdAndIsDeletedFalse(Integer id);
}