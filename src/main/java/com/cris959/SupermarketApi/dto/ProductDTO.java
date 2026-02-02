package com.cris959.SupermarketApi.dto;


public record ProductDTO(
        Long id,
        String nombre,
        String categoria,
        Double precio,
        Integer cantidad
) {
}
