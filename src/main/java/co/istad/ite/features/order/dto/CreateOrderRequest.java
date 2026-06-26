package co.istad.ite.features.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(

        @NotBlank(message = "Address is required")
        String address,

        @NotNull(message = "Discount is required")
        @Min(value = 0, message = "Discount must be between 0 and 100")
        @Max(value = 100, message = "Discount must be between 0 and 100")
        Float discount,

        @Size(max = 200, message = "Remark cannot exceed 200 characters")
        String remark,

        @NotEmpty(message = "Order must have at least one item")
        List<OrderLineDto> orderLines
) {
}
