package co.istad.ite.features.category;


import co.istad.ite.features.category.dto.CategoryResponse;
import co.istad.ite.features.category.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category mapCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    CategoryResponse mapCategoryToCategoryResponse(Category category);

}
