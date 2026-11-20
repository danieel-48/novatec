package com.novatec.api.dto;
import java.util.UUID;

public record CardStatusResponse(
    UUID cardId,
    String status
) {
}
