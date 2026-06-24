package co.istad.ite.features.product;

import co.istad.ite.features.product.dto.CreateProductRequest;
import co.istad.ite.features.product.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    public Page<ProductResponse> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        return productService.findAll(page, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createNew(@Valid @RequestBody CreateProductRequest createProductRequest) {
        return productService.createNew(createProductRequest);
    }

}