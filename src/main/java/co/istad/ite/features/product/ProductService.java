package co.istad.ite.features.product;

import co.istad.ite.features.product.dto.CreateProductRequest;
import co.istad.ite.features.product.dto.ProductResponse;
import co.istad.ite.features.product.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    //Find Product
    Page<ProductResponse> findAll(int page, int size);
    ProductResponse createNew(CreateProductRequest createProductRequest);

    ProductResponse updateProduct(Long id, UpdateProductRequest updateProductRequest);
}
