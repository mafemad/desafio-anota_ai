package com.mateus.desafioanotaai.domain.product.dto;

import com.mateus.desafioanotaai.domain.category.Category;

public record ProductResponseDTO(
        String id,
        String title,
        String description,
        Integer price,
        String ownerId,
        Category category
) {
}
