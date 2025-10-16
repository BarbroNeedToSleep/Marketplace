package se.lexicon.marketplace_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Builder;


@Builder
@Schema(description = "Request payload for creating an advertisement with user credentials")
public record CreateAdvertisementRequestDto(

        @Valid
        @Schema(description = "User credentials used to login or register automatically",
        example = "{\"email\":\"lina@example.com\",\"password\":\"Secret\"}")
        UserDto user,

        @Valid
        @Schema(description = "Advertisement details to be created",
                example = "{ \"title\": \"Cat tree, mostly unused\", \"description\": \"5 feet tall, many different layers\", \"expirationDate\": \"2025-12-31\", \"price\": 499.99 }")
        AdvertisementDto ad
) {
}
