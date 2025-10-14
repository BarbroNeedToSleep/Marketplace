package se.lexicon.marketplace_api.service;

import se.lexicon.marketplace_api.dto.AdvertisementDto;
import se.lexicon.marketplace_api.entity.Advertisement;

import java.util.List;

public interface AdvertisementService {

    Advertisement createAdvertisement(AdvertisementDto advertisementDto);

    List<Advertisement> findAllActiveAdvertisements();

    List<Advertisement> findByUserId(Integer userId);

    List<Advertisement> searchByTitleOrDescription(String keyword);
}
