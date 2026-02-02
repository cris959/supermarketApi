package com.cris959.SupermarketApi.dto;


public record ProductDTO(
        Long id,
        String name,
        String category,
        Double price,
        Integer stock
) {
}
