package com.cris959.SupermarketApi.dto;


public record OrderItemDTO(
        Long id,
        String nombreProd,
        Integer quantity,
        Double unitPrice,
        Double subTotal
) {
}
