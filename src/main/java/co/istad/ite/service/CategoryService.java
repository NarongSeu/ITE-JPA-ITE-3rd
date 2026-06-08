package co.istad.ite.service;

import co.istad.ite.dto.CategoryResponse;
import co.istad.ite.dto.CreateCategoryRequest;

public interface CategoryService {

    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);

}
