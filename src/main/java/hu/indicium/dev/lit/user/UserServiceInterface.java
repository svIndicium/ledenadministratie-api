package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.user.dto.NewUserDTO;

public interface UserServiceInterface {
    User createUser(NewUserDTO userDTO);
}
