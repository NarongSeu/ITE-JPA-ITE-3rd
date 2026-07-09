package co.istad.ite.payment.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String actNo,
        String actName,
        String actCurrent,
        BigDecimal balance,
        Boolean isHide,
        String accountType
) {
}
     