package com.mateus.desafioanotaai.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateProductDTO(
        @NotBlank String title,
        @NotBlank String description,
        @Min(1) Integer price,
        @NotBlank String ownerId,
        @NotBlank String categoryId
) {
}
