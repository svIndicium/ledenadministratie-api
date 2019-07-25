package hu.indicium.dev.lit.user;

public interface SignUpServiceInterface {
    void registerSignUp(String token);

    void signUp(SignUp signUp);

    User validate(String token);
}
