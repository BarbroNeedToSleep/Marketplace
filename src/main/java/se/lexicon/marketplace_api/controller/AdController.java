package se.lexicon.marketplace_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        this.userService = userService;
    }


    @Operation(summary = "Create an advertisement",
            description = "Auto-register user if new or validate credentials, then create ad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Advertisement created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "401", description = "Invalid user credentials")
    })
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


    @Operation(summary = "Get all active advertisements",
            description = "Return all ads that are not expired. Optional keyword search by title or description.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active advertisements returned")
    })
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


    @Operation(summary = "Get user's advertisements",
            description = "Return ads created by a specific user. Requires user credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's advertisements returned"),
            @ApiResponse(responseCode = "401", description = "Invalid user credentials")
    })
    @PostMapping("/my-ads")
    public ResponseEntity<List<AdvertisementDto>> getUserAds(@Valid @RequestBody UserDto loginDto) {

        UserDto user = userService.validateUser(loginDto.email(), loginDto.password());

        List<AdvertisementDto> userAds = advertisementService.findByUserId(user.id());
        return ResponseEntity.ok(userAds);
    }
}

