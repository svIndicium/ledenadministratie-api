package hu.indicium.dev.ledenadministratie.user.validation;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.util.Validator;
import hu.indicium.dev.ledenadministratie.util.ValidatorGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class UserValidatorConfiguration {

    @Bean
    Validator<Member> userValidator() {
        return new ValidatorGroup<>(Collections.emptyList());
    }
}
