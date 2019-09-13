package hu.indicium.dev.ledenadministratie.user.validation;

import hu.indicium.dev.ledenadministratie.user.User;
import hu.indicium.dev.ledenadministratie.util.Validator;

public class EmailValidator implements Validator<User> {

    public final static String EMPTY_EMAIL_ERROR_MESSAGE = "Empty email supplied";

    @Override
    public void validate(User user) {
        if (user.getEmail().isBlank()) {
            throw new IllegalArgumentException(EMPTY_EMAIL_ERROR_MESSAGE);
        }
        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email incorrect");
        }
    }
}
