package com.cris959.SupermarketApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representing an item in a sale")
public record OrderItemDTO(
        @Schema(description = "Unique identifier of the order item", example = "101")
        Long id,
        @Schema(description = "Name of the product", example = "Coca Cola 1.5L")
        String productName,
        @Schema(description = "Quantity of the product purchased", example = "2")
        Integer quantity,
        @Schema(description = "Price per unit of the product", example = "2.50")
        Double unitPrice,
        @Schema(description = "Subtotal for this item (quantity * unitPrice)", example = "5.00")
        Double subTotal
) {
}
