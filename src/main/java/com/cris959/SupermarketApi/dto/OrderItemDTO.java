package com.cris959.SupermarketApi.dto;


public record OrderItemDTO(
        Long id,
        String nombreProd,
        Integer cantProd,
        Double precio,
        Double subTotal
) {
}
