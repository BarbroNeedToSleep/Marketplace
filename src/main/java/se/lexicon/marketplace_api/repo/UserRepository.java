package se.lexicon.marketplace_api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.marketplace_api.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer>{

    Optional<User> findByEmailIgnoreCase(String email);
}
