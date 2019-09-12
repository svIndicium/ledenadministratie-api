package hu.indicium.dev.ledenadministratie.user.validation;

import hu.indicium.dev.ledenadministratie.user.User;
import hu.indicium.dev.ledenadministratie.util.Validator;

public class EmailValidator implements Validator<User> {

    @Override
    public void validate(User user) {
        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email incorrect");
        }
    }
}
