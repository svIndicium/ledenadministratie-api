package hu.indicium.dev.lit.userdata.exceptions;

public class UserDataAlreadyStoredException extends RuntimeException {
    public UserDataAlreadyStoredException() {
        super("User data already stored!");
    }
}
