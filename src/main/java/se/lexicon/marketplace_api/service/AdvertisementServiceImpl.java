package se.lexicon.marketplace_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.lexicon.marketplace_api.dto.AdvertisementDto;
import se.lexicon.marketplace_api.dto.UserDto;
import se.lexicon.marketplace_api.entity.Advertisement;
import se.lexicon.marketplace_api.entity.User;
import se.lexicon.marketplace_api.repo.AdvertisementRepository;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService{

    private final AdvertisementRepository advertisementRepository;
    private final UserService userService;


    @Override
    public AdvertisementDto createAdvertisementWithUser(String email, String password, AdvertisementDto adDto) {
        // UserDto from service
        UserDto userDto = userService.findByEmail(email);
        if (userDto == null) {
            userDto = userService.createUser(new UserDto(null, email, password));
        } else {
            userDto = userService.validateUser(email, password);
        }

        // Convert DTO → entity for Advertisement
        User userEntity = userDto.toEntity();

        Advertisement adEntity = adDto.toEntity(userEntity);
        Advertisement savedAd = advertisementRepository.save(adEntity);

        return AdvertisementDto.fromEntity(savedAd);
    }





    @Override
    public List<AdvertisementDto> findAllActiveAdvertisements() {
        return advertisementRepository.findByExpirationDateAfter(LocalDate.now())
                .stream()
                .map(AdvertisementDto::fromEntity)
                .toList();
    }


    @Override
    public List<AdvertisementDto> findByUserId(Integer userId) {
        return advertisementRepository.findByUserId(userId)
                .stream()
                .map(AdvertisementDto::fromEntity)
                .toList();
    }

    @Override
    public List<AdvertisementDto> searchByTitleOrDescription(String keyword) {
        return advertisementRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(AdvertisementDto::fromEntity)
                .toList();
    }
}
