package se.lexicon.marketplace_api.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;
import se.lexicon.marketplace_api.entity.Advertisement;
import se.lexicon.marketplace_api.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record AdvertisementDto(

        Integer id,

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 3, max = 1000, message = "Description must be between 3 and 1000 characters")
        String description,

        @Future(message = "Expiration date must be in the future")
        @NotNull(message = "Expiration date is required")
        LocalDate expirationDate,

        @NotNull(message = "Price is required")
        @DecimalMax(value = "1000000.00", message = "Price must be less than 1,000,000.00")
        @Positive(message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "User ID is required")
        @Positive(message = "User ID must be greater than 0")
        Integer userId
) {
    // --- Conversion method: Entity → DTO ---
    // adding this here to get the creation of user to work if they are making an advert and not being reg.
    public static AdvertisementDto fromEntity(Advertisement ad) {
        return new AdvertisementDto(
                ad.getId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getExpirationDate(),
                ad.getPrice(),
                ad.getUser().getId()
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
