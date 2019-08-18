package hu.indicium.dev.lit.register.validation.validators.registration;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator<Registration> {

    public static String ERROR_MESSAGE = "Email address incorrect";

    @Override
    public void validate(Registration validationEntity) {
        if (validationEntity.getEmail() == null || validationEntity.getEmail().equals("")) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        String email = validationEntity.getEmail();
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
