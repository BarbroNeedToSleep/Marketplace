package se.lexicon.marketplace_api.service;

import se.lexicon.marketplace_api.dto.AdvertisementDto;


import java.util.List;

public interface AdvertisementService {


    AdvertisementDto createAdvertisementWithUser(String email, String password, AdvertisementDto adDto);

    List<AdvertisementDto> findAllActiveAdvertisements();

    List<AdvertisementDto> findByUserId(Integer userId);

    List<AdvertisementDto> searchByTitleOrDescription(String keyword);
}
