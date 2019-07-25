package hu.indicium.dev.lit.user;

public interface UserServiceInterface {
    User createUser(SignUp signUp);

    User getUserById(Long userId);

    User getUserByAuthUserId(String authUserId);
}
