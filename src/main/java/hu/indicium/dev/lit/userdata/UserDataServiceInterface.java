package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.user.SignUp;
import hu.indicium.dev.lit.user.User;

public interface UserDataServiceInterface {
    UserData saveUserData(User user, SignUp signUp);

    UserData getUserData(Long userId);

    UserData updateUserData(UserData userData);

    void deleteUserData(Long userId);

    boolean exists(Long userId);
}
