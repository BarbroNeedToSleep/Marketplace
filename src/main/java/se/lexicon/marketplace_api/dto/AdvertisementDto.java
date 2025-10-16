package se.lexicon.marketplace_api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import se.lexicon.marketplace_api.entity.Advertisement;
import se.lexicon.marketplace_api.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record AdvertisementDto(

        @Schema(description = "Unique ID of the advertisement", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Integer id,

        @Schema(description = "Title of the advertisement", example = "Cat tree, mostly unused")
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
        String title,

        @Schema(description = "Description of the advertisement", example = "5 feet tall, many different layers")
        @NotBlank(message = "Description is required")
        @Size(min = 3, max = 1000, message = "Description must be between 3 and 1000 characters")
        String description,

        @Schema(description = "Expiration date of the advertisement", example = "2025-12-31")
        @Future(message = "Expiration date must be in the future")
        @NotNull(message = "Expiration date is required")
        LocalDate expirationDate,

        @Schema(description = "Price of the advertisement", example = "199.99")
        @NotNull(message = "Price is required")
        @DecimalMax(value = "1000000.00", message = "Price must be less than 1,000,000.00")
        @Positive(message = "Price must be greater than 0")
        BigDecimal price

) {
    // --- Conversion method: Entity → DTO ---
    // adding this here to get the creation of user to work if they are making an advert and not being reg.
    public static AdvertisementDto fromEntity(Advertisement ad) {
        return new AdvertisementDto(
                ad.getId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getExpirationDate(),
                ad.getPrice()
        );
    }

    // --- Conversion method: DTO → Entity ---
    public Advertisement toEntity(User user) {
        return Advertisement.builder()
                .id(this.id())
                .title(this.title())
                .description(this.description())
                .expirationDate(this.expirationDate())
                .price(this.price())
                .user(user)
                .build();
    }
}
