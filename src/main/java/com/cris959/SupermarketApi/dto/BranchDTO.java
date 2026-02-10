package com.cris959.SupermarketApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Branch entity")
public record BranchDTO(
        @Schema(description = "Unique identifier of the branch", example = "1")
        Long id,
        @Schema(description = "Name of the branch", example = "Central Branch", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
        @Schema(description = "Physical address of the branch", example = "123 Main St", requiredMode = Schema.RequiredMode.REQUIRED)
        String address,
        @Schema(description = "Indicates if the branch is active or logically deleted", example = "true")
        Boolean active
) {
}
