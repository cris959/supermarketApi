package com.cris959.SupermarketApi.dto;


public record OrderItemDTO(
        Long id,
        String productName,
        Integer quantity,
        Double unitPrice,
        Double subTotal
) {
}
