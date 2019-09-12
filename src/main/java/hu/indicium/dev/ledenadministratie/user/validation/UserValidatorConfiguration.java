package hu.indicium.dev.ledenadministratie.user.validation;

import hu.indicium.dev.ledenadministratie.user.User;
import hu.indicium.dev.ledenadministratie.util.Validator;
import hu.indicium.dev.ledenadministratie.util.ValidatorGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class UserValidatorConfiguration {

    @Bean
    Validator<User> userValidator() {
        return new ValidatorGroup<>(Arrays.asList(new EmailValidator()));
    }
}
