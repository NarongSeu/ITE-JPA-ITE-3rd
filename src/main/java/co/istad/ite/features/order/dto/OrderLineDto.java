package co.istad.ite.features.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderLineDto(
        @NotBlank(message = "Code is Required")
        String code,

        @Positive(message = "Quantity must be positive")
        @NotNull(message = "Quantity is required")
        Integer qty,

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be positive")
        BigDecimal unitPrice
) {
}
