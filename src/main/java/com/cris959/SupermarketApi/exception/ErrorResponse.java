package com.cris959.SupermarketApi.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard structure for API error responses")
public record ErrorResponse(
        @Schema(description = "HTTP status code", example = "404")
        int status,
        @Schema(description = "Detailed error message", example = "Resource not found")
        String message,
        @Schema(description = "Timestamp when the error occurred", example = "2026-02-10T18:30:00")
        LocalDateTime timestamp
) {
}
