package co.istad.ite.payment.dto.request;

import co.istad.ite.payment.util.CurrencyUtil;

import java.math.BigDecimal;

public record CreateAccountRequest(
        String actNo,
        String actName,
        CurrencyUtil actCurrency,
        BigDecimal balance,
        String accountType,
        String phoneNumber
) {
}
