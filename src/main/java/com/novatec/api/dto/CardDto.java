package com.novatec.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record CardDto(
    @NotBlank @Pattern(regexp = "^[0-9]{6}$", message = "productId debe tener 6 dígitos")
    String productId,
    @NotBlank (message= "holderName es obligatorio")
    @Size(max = 150, message = "holderName debe tener como máximo 150 caracteres")
    String holderName
) {
}


