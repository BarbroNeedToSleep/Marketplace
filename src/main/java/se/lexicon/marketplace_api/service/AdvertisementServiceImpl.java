package se.lexicon.marketplace_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.lexicon.marketplace_api.dto.AdvertisementDto;
import se.lexicon.marketplace_api.entity.Advertisement;
import se.lexicon.marketplace_api.entity.User;
import se.lexicon.marketplace_api.repo.AdvertisementRepository;
import se.lexicon.marketplace_api.repo.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService{

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;


    @Override
    public Advertisement createAdvertisement(AdvertisementDto advertisementDto) {

        User user = userRepository.findById(advertisementDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Advertisement ad = Advertisement.builder()
                .title(advertisementDto.title())
                .description(advertisementDto.description())
                .expirationDate(advertisementDto.expirationDate())
                .price(advertisementDto.price())
                .user(user)
                .build();

        return advertisementRepository.save(ad);
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
