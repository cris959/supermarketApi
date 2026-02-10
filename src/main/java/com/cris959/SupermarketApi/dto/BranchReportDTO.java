package com.cris959.SupermarketApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for branch total sales report")
public record BranchReportDTO(
        @Schema(description = "Unique identifier of the branch", example = "1")
        Long branchId,
        @Schema(description = "Name of the branch", example = "Central Branch")
        String branchName,
        @Schema(description = "Total revenue collected by the branch", example = "5000.50")
        Double totalCollected
) {
}