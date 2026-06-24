package co.istad.ite.features.product;

import co.istad.ite.features.category.Category;
import co.istad.ite.features.category.CategoryRepository;
import co.istad.ite.features.product.dto.CreateProductRequest;
import co.istad.ite.features.product.dto.ProductResponse;
import co.istad.ite.util.GenerateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Override
    public Page<ProductResponse> findAll(int page, int size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sortById);

        Page<Product> products = productRepository
                .findAll(pageRequest);
        //Map for product response
        return products.map(productMapper::mapProductToProductResponse);
    }

    @Override
    public ProductResponse createNew(CreateProductRequest createProductRequest) {

        // Validate product name
        if (productRepository.existsByName(createProductRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Product name has already been used");
        }

        // Validate category ID
        Category category = categoryRepository.findById(createProductRequest.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category has not been found")); // 🚨 CRASHES HERE
        // Transfer data from DTO to Model
        Product product = productMapper
                .mapCreateProductRequestToProduct(createProductRequest);


        // 4. Set generated system data
        product.setCategory(category);
        product.setCode(GenerateUtils.generateProductCode());
        product.setSlug(GenerateUtils.generateSlug(createProductRequest.name()));
        product.setIsAvailable(true);
        product.setIsDelete(false);

        // 5. Save product
        product = productRepository.save(product);

        return productMapper.mapProductToProductResponse(product);
    }

    @Override
    public ProductResponse updateProductRequest(String name, String description, BigDecimal unitPrice) {

        return null;
    }
}
