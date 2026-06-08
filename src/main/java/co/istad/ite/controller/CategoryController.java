package co.istad.ite.controller;


import co.istad.ite.dto.CategoryResponse;
import co.istad.ite.dto.CreateCategoryRequest;
import co.istad.ite.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public CategoryResponse createCategory() {
//
//    }
}
