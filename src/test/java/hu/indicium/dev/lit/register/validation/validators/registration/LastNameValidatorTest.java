package hu.indicium.dev.lit.register.validation.validators.registration;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Last name validator")
@Tag("Validations")
class LastNameValidatorTest {

    @Test
    @DisplayName("Validate last name")
    void validate() {
        Validator<Registration> validator = new LastNameValidator();
        Registration registration = new Registration();
        registration.setLastName("John");
        validator.validate(registration);
    }

    @Test
    @DisplayName("Validate empty last name")
    void validate_shouldThrowException_ifLastNameIsEmpty() {
        Validator<Registration> validator = new LastNameValidator();
        Registration registration = new Registration();
        registration.setLastName("");
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(LastNameValidator.ERROR_MESSAGE);
        }
    }

    @Test
    @DisplayName("Validate null last name")
    void validate_shouldThrowException_ifLastNameIsNull() {
        Validator<Registration> validator = new LastNameValidator();
        Registration registration = new Registration();
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(LastNameValidator.ERROR_MESSAGE);
        }
    }
}