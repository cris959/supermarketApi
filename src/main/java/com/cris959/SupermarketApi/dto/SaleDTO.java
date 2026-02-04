package com.cris959.SupermarketApi.dto;


import com.cris959.SupermarketApi.model.SaleStatus;

import java.time.LocalDate;
import java.util.List;

public record SaleDTO(
        // Datos de la Venta
        Long id,
        LocalDate date,
        SaleStatus status,

        // Datos de la sucursal
        Long branchId,
        String branchName,

        // Lista detalles
        List<OrderItemDTO> items,

        // Total venta
       Double total
) {
}
