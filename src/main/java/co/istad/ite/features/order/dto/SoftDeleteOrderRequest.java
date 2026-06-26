package co.istad.ite.features.order.dto;

import jakarta.validation.constraints.NotNull;

public record SoftDeleteOrderRequest(
        @NotNull(message = "isDeleted is required.")
        Boolean isDeleted
) {
}
