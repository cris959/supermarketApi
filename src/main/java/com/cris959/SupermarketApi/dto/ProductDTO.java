package com.cris959.SupermarketApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Product entity")
public record ProductDTO(
        @Schema(description = "Unique identifier of the product", example = "1")
        Long id,
        @Schema(description = "Name of the product", example = "Coca Cola 1.5L", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
        @Schema(description = "Category the product belongs to", example = "Beverages")
        String category,
        @Schema(description = "Price of the product", example = "2.50", requiredMode = Schema.RequiredMode.REQUIRED)
        Double price,
        @Schema(description = "Current stock level", example = "100")
        Integer stock,
        @Schema(description = "Indicates if the product is active or logically deleted", example = "true")
        Boolean active
) {
}
