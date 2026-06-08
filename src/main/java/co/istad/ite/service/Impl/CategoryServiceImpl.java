package co.istad.ite.service.Impl;

import co.istad.ite.domain.Category;
import co.istad.ite.dto.CategoryResponse;
import co.istad.ite.dto.CreateCategoryRequest;
import co.istad.ite.dto.UpdateCategoryRequest;
import co.istad.ite.mapper.CategoryMapper;
import co.istad.ite.repository.CategoryRepository;
import co.istad.ite.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<Category> categoryPage = categoryRepository.findAllByIsDeleted(false,pageable);

        return categoryPage.map(categoryMapper::mapCategoryToCategoryResponse);
    }

    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {
        log.info("createNew {}", createCategoryRequest);
        boolean isExisting = categoryRepository
                .existsByName(createCategoryRequest.name());
        if (isExisting) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category has already been created"
            );
        }

        Category parentCategory = null;

//        CategoryResponse parentCateResponse = null;

        if (createCategoryRequest.parentCategoryId() != null) {
             parentCategory = categoryRepository.findById(createCategoryRequest.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent Category Not Found"));

        }

        Category category = categoryMapper.mapCategoryRequestToCategory
                (createCategoryRequest );

        category.setIsDeleted(false);
        category.setParentCategory(parentCategory);

        category = categoryRepository.save(category);


        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        return categoryRepository.findById(id).map(categoryMapper::mapCategoryToCategoryResponse).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
    }

    @Override
    public void hardDeleteById(Integer id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));

        categoryRepository.delete(category);
    }

    @Override
    public void softDeleteById(Integer id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        category.setIsDeleted(true);
        for (Category c : category.getChildCategories()){
            c.setIsDeleted(true);
        }
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getSubCategories(Integer parentCategoryId) {
        Category category = categoryRepository.findById(parentCategoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        if(category.getIsDeleted()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This category is already deleted.");
        }
        return categoryRepository.findAllByIsDeletedAndParentCategory(false,category).stream().map(categoryMapper::mapCategoryToCategoryResponse).toList();
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        if(category.getIsDeleted()==true){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This category is already deleted.");
        }
        if(updateCategoryRequest.name() !=null){
            category.setName(updateCategoryRequest.name());
        }
        if(updateCategoryRequest.description()!=null){
            category.setDescription(updateCategoryRequest.description());
        }
        if(updateCategoryRequest.icon()!=null){
            category.setIcon(updateCategoryRequest.icon());
        }
        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }


}
