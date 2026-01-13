package com.mateus.desafioanotaai.domain.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDTO(
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String ownerId) {
}
