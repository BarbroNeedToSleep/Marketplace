package se.lexicon.marketplace_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Schema(description = "Response object for error handling")
public record ErrorResponse(
        @Schema(description = "HTTP status code of the error", example = "404")
        int status,
        @Schema(description = "Array of error messages", example = "[\"Resource not found\", \"title must not be empty\"]")
        String[] errors,
        @Schema(description = "Timestamp when the error occurred", example = "2025-10-17T16:50:00")
        LocalDateTime timestamp
) {

    // Constructor automatically sets timestamp to now if not provided
    public ErrorResponse(int status, String[] errors) {
        this(status, errors, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
