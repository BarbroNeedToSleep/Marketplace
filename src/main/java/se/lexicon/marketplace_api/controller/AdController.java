package se.lexicon.marketplace_api.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.marketplace_api.dto.AdvertisementDto;
import se.lexicon.marketplace_api.dto.CreateAdvertisementRequestDto;
import se.lexicon.marketplace_api.dto.UserDto;
import se.lexicon.marketplace_api.service.AdvertisementService;
import se.lexicon.marketplace_api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/ads")


@Validated
public class AdController {

    private final AdvertisementService advertisementService;
    private final UserService userService;

    public AdController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService=userService;
    }

    // 1️Create Advertisement (auto-register or validate user)
    @PostMapping
    public ResponseEntity<AdvertisementDto> createAd(
            @Valid @RequestBody CreateAdvertisementRequestDto request
    ) {
        UserDto user = request.user();
        AdvertisementDto ad = request.ad();

        AdvertisementDto createdAd = advertisementService.createAdvertisementWithUser(
                user.email(),
                user.password(),
                ad
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }


    // Get All Active Ads with optional keyword search
    @GetMapping
    public ResponseEntity<List<AdvertisementDto>> getAllActiveAds(
            @RequestParam(required = false) String keyword
    ) {
        List<AdvertisementDto> ads;
        if (keyword != null && !keyword.isBlank()) {
            ads = advertisementService.searchByTitleOrDescription(keyword);
        } else {
            ads = advertisementService.findAllActiveAdvertisements();
        }
        return ResponseEntity.ok(ads);
    }

    // Get Ads for a specific user
    @PostMapping("/my-ads")
    public ResponseEntity<List<AdvertisementDto>> getUserAds(@Valid @RequestBody UserDto loginDto) {
        // Validate user credentials
        UserDto user = userService.validateUser(loginDto.email(), loginDto.password());
        // Fetch ads for this user
        List<AdvertisementDto> userAds = advertisementService.findByUserId(user.id());
        return ResponseEntity.ok(userAds);
    }
}
