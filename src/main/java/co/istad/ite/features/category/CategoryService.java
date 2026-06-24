package co.istad.ite.features.category;

import co.istad.ite.features.category.dto.CategoryResponse;
import co.istad.ite.features.specification_dto.RequestDto;
import co.istad.ite.features.category.dto.CreateCategoryRequest;
import co.istad.ite.features.category.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize);

    List<CategoryResponse> findByCriteria(RequestDto requestDto );

    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);

    CategoryResponse getCategoryById(Integer id);

    void hardDeleteById(Integer id);
    void softDeleteById(Integer id);

    List<CategoryResponse> getSubCategories(Integer parentCategoryId);

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);

}
