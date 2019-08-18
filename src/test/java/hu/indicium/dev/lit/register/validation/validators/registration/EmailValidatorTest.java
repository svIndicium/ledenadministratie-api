package hu.indicium.dev.lit.register.validation.validators.registration;

import hu.indicium.dev.lit.register.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Email validator")
@Tag("Validations")
class EmailValidatorTest {

    @Test
    @DisplayName("Validate email")
    void validate() {
        EmailValidator validator = new EmailValidator();
        Registration registration = new Registration();
        registration.setEmail("john@doe.com");
        validator.validate(registration);
    }

    @Test
    @DisplayName("Validate invalid email")
    void validate_shouldThrowException_ifEmailIsInvalid() {
        EmailValidator validator = new EmailValidator();
        Registration registration = new Registration();
        registration.setEmail("john@doe");
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(EmailValidator.ERROR_MESSAGE);
        }
    }

    @Test
    @DisplayName("Validate empty email")
    void validate_shouldThrowException_ifEmailIsEmpty() {
        EmailValidator validator = new EmailValidator();
        Registration registration = new Registration();
        registration.setEmail("");
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(EmailValidator.ERROR_MESSAGE);
        }
    }

    @Test
    @DisplayName("Validate null email")
    void validate_shouldThrowException_ifEmailIsNull() {
        EmailValidator validator = new EmailValidator();
        Registration registration = new Registration();
        try {
            validator.validate(registration);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(EmailValidator.ERROR_MESSAGE);
        }
    }
}