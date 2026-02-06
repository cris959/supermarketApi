package com.cris959.SupermarketApi.dto;

public record BranchReportDTO(
        Long branchId,
        String branchName,
        Double totalCollected
) {
}