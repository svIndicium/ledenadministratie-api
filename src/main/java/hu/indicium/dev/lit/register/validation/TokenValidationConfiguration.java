package hu.indicium.dev.lit.register.validation;

import hu.indicium.dev.lit.register.Token;
import hu.indicium.dev.lit.register.validation.validators.token.TokenValidator;
import hu.indicium.dev.lit.util.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenValidationConfiguration {
    @Bean
    Validator<Token> tokenValidator() {
        return new TokenValidator();
    }
}
