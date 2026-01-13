package com.mateus.desafioanotaai.domain.product.dto;

public record UpdateProductDTO(
        String title,
        String description,
        Integer price,
        String categoryId
) {
}
