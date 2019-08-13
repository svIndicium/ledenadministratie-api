package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.user.dto.UserDTO;

public interface UserServiceInterface {
    UserDTO createUser(RegistrationDTO registration);

    User getUserById(Long userId);

    User getUserByAuthUserId(String authUserId);
}
