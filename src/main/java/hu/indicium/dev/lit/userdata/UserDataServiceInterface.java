package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.user.User;

public interface UserDataServiceInterface {
    UserData saveUserData(User user, Registration registration);

    UserData getUserData(Long userId);

    UserData updateUserData(UserData userData);

    void deleteUserData(Long userId);

    boolean exists(Long userId);
}
