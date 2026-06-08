package co.istad.ite.mapper;


import co.istad.ite.domain.Category;
import co.istad.ite.dto.CategoryResponse;
import co.istad.ite.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category mapCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    CategoryResponse mapCategoryToCategoryResponse(Category category);

}
