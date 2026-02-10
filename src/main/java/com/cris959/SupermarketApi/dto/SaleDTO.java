package com.cris959.SupermarketApi.dto;


import com.cris959.SupermarketApi.model.SaleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Data Transfer Object for Sale entity and its details")
public record SaleDTO(
        // Datos de la Venta
        @Schema(description = "Unique identifier of the sale", example = "1001")
        Long id,
        @Schema(description = "Date of the sale", example = "2026-02-10")
        LocalDate date,
        @Schema(description = "Current status of the sale", example = "COMPLETED")
        SaleStatus status,

        // Datos de la sucursal
        @Schema(description = "Identifier of the branch where the sale occurred", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long branchId,
        @Schema(description = "Name of the branch")
        String branchName,

        // Lista detalles
        @Schema(description = "List of items included in the sale")
        List<OrderItemDTO> items,

        // Total venta
        @Schema(description = "Total amount of the sale", example = "150.75")
        Double total
) {
}
