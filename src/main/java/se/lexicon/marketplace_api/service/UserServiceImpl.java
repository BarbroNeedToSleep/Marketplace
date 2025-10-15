package se.lexicon.marketplace_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.lexicon.marketplace_api.dto.UserDto;
import se.lexicon.marketplace_api.entity.User;
import se.lexicon.marketplace_api.repo.UserRepository;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElse(null);
    }

    @Override
    public User createUser(UserDto userDTO) {
        return userRepository.save(userDTO.toEntity());
    }

    @Override
    public User validateUser(String email, String password) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(user -> {
                    if (!user.getPassword().equals(password)) {
                        throw new RuntimeException("Invalid password");
                    }
                    return user;
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
