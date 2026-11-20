package com.novatec.api.dto;

import jakarta.validation.constraints.Size;

public record BlockCardRequest(
    @Size(max = 255, message = "reason debe tener como máximo 255 caracteres")
    String reason
){}