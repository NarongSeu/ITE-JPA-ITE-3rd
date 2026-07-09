package co.istad.ite.features.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NotFound;

import java.math.BigDecimal;

public record UpdateProductRequest(

        @NotNull(message = "ID is required")
        Long id,

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 255)
        String name,

        @NotBlank(message = "Description is required")
        @Size(min = 3, max = 10000)
        String description,

        @NotBlank(message = "Thumbnail is required")
        String thumbnail,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be Positive")
        BigDecimal unitPrice,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be Positive")
        Integer qty,

        @NotNull(message = "Category ID is required")
        @Positive(message = "Category ID must be Positive")
        Long categoryId
) {
}
