package co.istad.ite.payment.dto.request;

import java.math.BigDecimal;

public record UpdateAccountRequest(
        String actName,
        BigDecimal overLimit
) {
}
