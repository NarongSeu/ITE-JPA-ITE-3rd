package co.istad.ite.service.Impl;

import co.istad.ite.domain.Category;
import co.istad.ite.dto.CategoryResponse;
import co.istad.ite.dto.CreateCategoryRequest;
import co.istad.ite.repository.CategoryRepository;
import co.istad.ite.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {
        log.info("createNew {}", createCategoryRequest);
        return null;
    }


}
