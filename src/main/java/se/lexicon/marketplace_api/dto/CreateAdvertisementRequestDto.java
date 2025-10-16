package se.lexicon.marketplace_api.dto;

import jakarta.validation.Valid;
import lombok.Builder;


@Builder
public record CreateAdvertisementRequestDto(

        @Valid UserDto user,
        @Valid AdvertisementDto ad
) {
}
