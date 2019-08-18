package hu.indicium.dev.lit.register.validation.validators.registration;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.util.Validator;

public class LastNameValidator implements Validator<Registration> {

    public static final String ERROR_MESSAGE = "Last name invalid";

    @Override
    public void validate(Registration validationEntity) {
        if (validationEntity.getLastName() == null || validationEntity.getLastName().equals("")) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
