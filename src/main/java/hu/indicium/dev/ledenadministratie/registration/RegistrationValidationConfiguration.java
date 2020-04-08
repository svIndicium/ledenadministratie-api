package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.validation.CommentValidator;
import hu.indicium.dev.ledenadministratie.util.ValidatorGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
public class RegistrationValidationConfiguration {

    @Bean
    ValidatorGroup<Registration> registrationValidatorGroup() {
        return new ValidatorGroup<>(Arrays.asList(new CommentValidator()));
    }
}
