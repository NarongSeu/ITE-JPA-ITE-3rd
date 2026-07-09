package co.istad.ite.features.product;


import co.istad.ite.features.product.dto.CreateProductRequest;
import co.istad.ite.features.product.dto.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapCreateProductRequestToProduct(CreateProductRequest createProductRequest);
    ProductResponse mapProductToProductResponse(Product product);

}