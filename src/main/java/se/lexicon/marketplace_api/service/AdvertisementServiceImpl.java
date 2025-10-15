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
        User user = userService.findByEmail(email);
        if (user == null) {
            user = userService.createUser(new UserDto(null, email, password));
        } else {
            user = userService.validateUser(email, password); // use the returned user
        }

        Advertisement adEntity = adDto.toEntity(user);
        Advertisement savedAd = advertisementRepository.save(adEntity);
        return AdvertisementDto.fromEntity(savedAd);
    }

    @Override
    public List<Advertisement> findAllActiveAdvertisements() {

        return advertisementRepository.findByExpirationDateAfter(LocalDate.now());
    }

    @Override
    public List<Advertisement> findByUserId(Integer userId) {

        return advertisementRepository.findByUserId(userId);
    }

    @Override
    public List<Advertisement> searchByTitleOrDescription(String keyword) {

        return advertisementRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

}
