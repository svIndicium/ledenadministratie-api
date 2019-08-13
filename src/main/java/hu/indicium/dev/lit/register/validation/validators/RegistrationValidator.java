package hu.indicium.dev.lit.register.validation.validators;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.util.Validator;

import java.util.List;

public class RegistrationValidator implements Validator<Registration> {

    private List<Validator<Registration>> validators;

    public RegistrationValidator(List<Validator<Registration>> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(Registration registration) {
        validators.forEach(registrationValidator -> validate(registration));
    }
}
