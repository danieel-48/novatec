package com.novatec.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CardIssuedResponse(
    UUID cardId,
    String cardNumber,
    String status,
    LocalDate expiresAt,
    BigDecimal balance,
    String currency
) {
}
