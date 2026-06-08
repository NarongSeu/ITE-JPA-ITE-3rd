package co.istad.ite.service;

import co.istad.ite.dto.CategoryResponse;
import co.istad.ite.dto.CreateCategoryRequest;
import co.istad.ite.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize);

    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);

    CategoryResponse getCategoryById(Integer id);

    void hardDeleteById(Integer id);
    void softDeleteById(Integer id);

    List<CategoryResponse> getSubCategories(Integer parentCategoryId);

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);

}
