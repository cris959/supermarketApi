package com.cris959.SupermarketApi.dto;


import java.time.LocalDate;
import java.util.List;

public record SaleDTO(
        // Datos de la Venta
        Long id,
        LocalDate fecha,
        String estado,

        // Datos de la sucursal
        Long idSucursal,

        // Lista detalles
        List<OrderItemDTO> detalle,

        // Total venta
       Double total
) {
}
