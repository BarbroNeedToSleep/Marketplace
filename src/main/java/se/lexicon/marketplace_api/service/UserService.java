package se.lexicon.marketplace_api.service;

import se.lexicon.marketplace_api.dto.UserDto;
import se.lexicon.marketplace_api.entity.User;

public interface UserService {

    UserDto findByEmail(String email);

    UserDto createUser(UserDto userDTO);

    UserDto validateUser(String email, String password);
}
