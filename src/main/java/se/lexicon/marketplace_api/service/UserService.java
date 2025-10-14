package se.lexicon.marketplace_api.service;

import se.lexicon.marketplace_api.dto.UserDto;
import se.lexicon.marketplace_api.entity.User;

public interface UserService {

    User findByEmail(String email);

    User createUser(UserDto userDTO);

    User validateUser(String email, String password);
}
