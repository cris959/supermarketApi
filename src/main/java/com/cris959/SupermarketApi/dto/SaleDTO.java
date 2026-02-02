package com.cris959.SupermarketApi.dto;


import java.time.LocalDate;
import java.util.List;

public record SaleDTO(
        // Datos de la Venta
        Long id,
        LocalDate date,
        String status,

        // Datos de la sucursal
        Long branchId,
        String branchName,

        // Lista detalles
        List<OrderItemDTO> items,

        // Total venta
       Double total
) {
}
