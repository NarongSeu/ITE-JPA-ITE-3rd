package co.istad.ite.features.order.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentStatusRequest(
        @NotNull(message = "Payment status is required (true = paid, false = unpaid)")
        Boolean status
) {
}
