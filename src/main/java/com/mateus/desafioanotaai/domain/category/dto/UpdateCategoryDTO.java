package com.mateus.desafioanotaai.domain.category.dto;

import jakarta.validation.constraints.Null;

public record UpdateCategoryDTO(
        String title,
        String description) {
}
