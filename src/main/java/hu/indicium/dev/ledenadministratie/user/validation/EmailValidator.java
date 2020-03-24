package hu.indicium.dev.ledenadministratie.user.validation;

import hu.indicium.dev.ledenadministratie.user.User;
import hu.indicium.dev.ledenadministratie.util.Validator;

public class EmailValidator implements Validator<User> {

    public static final String EMPTY_EMAIL_ERROR_MESSAGE = "Empty email supplied";

    public static final String EMAIL_INCORRECT = "Email incorrect";

    @Override
    public void validate(User user) {
        if (user.getMailAddresses().get(0).getMailAddress().isBlank()) {
            throw new IllegalArgumentException(EMPTY_EMAIL_ERROR_MESSAGE);
        }
    }
}
