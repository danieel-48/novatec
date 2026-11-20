package com.novatec.api.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CardDetailResponse(
    UUID cardId,
    String cardNumber,
    String holderName,
    String status,
    String blockedAt,
    String blockedReason,
    LocalDate expiresAt,
    BigDecimal balance,
    String currency
) {}
