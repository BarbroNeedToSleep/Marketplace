package se.lexicon.marketplace_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.marketplace_api.entity.Advertisement;

import java.time.LocalDate;
import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

    List<Advertisement> findByExpirationDateAfter(LocalDate date);
    List<Advertisement> findByUserId(Integer userId);

    List<Advertisement> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase
            (String title, String description);
}
