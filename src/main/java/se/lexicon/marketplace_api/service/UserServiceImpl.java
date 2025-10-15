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
    public UserDto findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(UserDto::fromEntity)
                .orElse(null);
    }

    @Override
    public UserDto createUser(UserDto userDTO) {
        User saved = userRepository.save(userDTO.toEntity());
        return UserDto.fromEntity(saved);
    }

    @Override
    public UserDto validateUser(String email, String password) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return UserDto.fromEntity(user);
    }
}
