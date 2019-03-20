package hu.indicium.dev.lit.userdata.exceptions;

public class UserDataNotFoundException extends RuntimeException {
    public UserDataNotFoundException() {
        super("User data not found!");
    }
}
