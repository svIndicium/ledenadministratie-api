package hu.indicium.dev.lit.register.validation.validators;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

@DisplayName("Registration Validator")
@Tag("Validations")
class RegistrationValidatorTest {

    @Test
    @DisplayName("Test validation")
    void validate() {
        Validator<Registration> validationMock1 = Mockito.mock(Validator.class);
        Validator<Registration> validationMock2 = Mockito.mock(Validator.class);
        Validator<Registration> registrationValidator = new RegistrationValidator(Arrays.asList(validationMock1, validationMock2));
        Registration registration = new Registration();
        registrationValidator.validate(registration);
        verify(validationMock1).validate(registration);
        verify(validationMock2).validate(registration);
    }
}