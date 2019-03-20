package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.user.User;
import hu.indicium.dev.lit.user.dto.NewUserDTO;

public interface UserDataServiceInterface {
    UserData saveUserData(User user, NewUserDTO userDTO);

    UserData getUserData(Long userId);

    UserData updateUserData(UserData userData);
}
