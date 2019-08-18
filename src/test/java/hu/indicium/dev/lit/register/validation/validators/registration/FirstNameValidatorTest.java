package hu.indicium.dev.lit.register.validation.validators.registration;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("First name validator")
@Tag("Validations")
class FirstNameValidatorTest {

    @Test
    @DisplayName("Validate first name")
    void validate() {
        Validator<Registration> validator = new FirstNameValidator();
        Registration registration = new Registration();
        registration.setFirstName("John");
        validator.validate(registration);
    }

    @Test
    @DisplayName("Validate first name containing special characters")
    void validate_shouldThrowException_ifFirstNameContainsSpecialCharacters() {
        Validator<Registration> validator = new FirstNameValidator();
        Registration registration = new Registration();
        registration.setFirstName("john@doe");
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(FirstNameValidator.ERROR_MESSAGE);
        }
    }

    @Test
    @DisplayName("Validate empty first name")
    void validate_shouldThrowException_ifFirstNameIsEmpty() {
        Validator<Registration> validator = new FirstNameValidator();
        Registration registration = new Registration();
        registration.setFirstName("");
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(FirstNameValidator.ERROR_MESSAGE);
        }
    }

    @Test
    @DisplayName("Validate null first name")
    void validate_shouldThrowException_ifFirstNameIsNull() {
        Validator<Registration> validator = new FirstNameValidator();
        Registration registration = new Registration();
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(FirstNameValidator.ERROR_MESSAGE);
        }
    }
}