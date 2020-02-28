package hu.indicium.dev.ledenadministratie.registration.validation;

import hu.indicium.dev.ledenadministratie.registration.Registration;
import hu.indicium.dev.ledenadministratie.util.Validator;
import hu.indicium.dev.ledenadministratie.util.ValidatorGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class RegistrationValidatorConfiguration {
    @Bean
    Validator<Registration> registrationValidator() {
        return new ValidatorGroup<>(Arrays.asList(new CommentValidator()));
    }
}
