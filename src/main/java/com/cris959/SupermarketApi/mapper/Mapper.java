package com.cris959.SupermarketApi.mapper;


import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.model.Product;

public class Mapper {

    // Mapeo de Producto a ProductoDTO
    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock()
        );

    }

    // Mapeo de Venta a VentaDTO


    // Mapeo de Sucursal a SucuarsalDTO


}
