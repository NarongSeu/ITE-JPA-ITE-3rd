package co.istad.ite.features.product;

import co.istad.ite.features.product.dto.CreateProductRequest;
import co.istad.ite.features.product.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ProductService {

    //Find Product
    Page<ProductResponse> findAll(int page, int size);
    ProductResponse createNew(CreateProductRequest createProductRequest);

    ProductResponse updateProductRequest(String name, String description, BigDecimal unitPrice);

}
