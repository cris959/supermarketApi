package com.cris959.SupermarketApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for global total sales report across all branches")
public record GlobalReportDTO(
        @Schema(description = "Name of the report", example = "Total Supermarket Revenue")
        String reportName,
        @Schema(description = "Total revenue collected across all branches", example = "150000.75")
        Double totalCollected
) {
}
