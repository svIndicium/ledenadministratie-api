package hu.indicium.dev.lit.register.validation;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.register.validation.validators.RegistrationValidator;
import hu.indicium.dev.lit.register.validation.validators.registration.EmailValidator;
import hu.indicium.dev.lit.register.validation.validators.registration.FirstNameValidator;
import hu.indicium.dev.lit.register.validation.validators.registration.LastNameValidator;
import hu.indicium.dev.lit.util.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class RegistrationValidationConfiguration {
    @Bean
    Validator<Registration> registrationValidator() {
        return new RegistrationValidator(Arrays.asList(new FirstNameValidator(), new LastNameValidator(), new EmailValidator()));
    }
}
