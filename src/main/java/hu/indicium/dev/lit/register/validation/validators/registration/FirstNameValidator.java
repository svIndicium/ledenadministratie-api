package hu.indicium.dev.lit.register.validation.validators.registration;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstNameValidator implements Validator<Registration> {

    public static final String ERROR_MESSAGE = "First name invalid";

    @Override
    public void validate(Registration validationEntity) {
        if (validationEntity.getFirstName() == null || validationEntity.getFirstName().equals("")) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        String firstName = validationEntity.getFirstName();
        Pattern pattern = Pattern.compile("^(?=.{1,40}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$");
        Matcher matcher = pattern.matcher(firstName);
        if (!matcher.find()) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
