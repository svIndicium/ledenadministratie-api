package hu.indicium.dev.ledenadministratie.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@DisplayName("Validator Group")
class ValidatorGroupTest {

    @Mock
    Validator<String> validator;

    @Mock
    Validator<String> validator1;

    @Test
    @DisplayName("Run all validations when validating")
    void shouldRunAllValidations_whenValidating() {
        ValidatorGroup<String> validatorGroup = new ValidatorGroup<>(Arrays.asList(validator, validator1));
        validatorGroup.validate("testString");

        verify(validator).validate(any(String.class));
        verify(validator1).validate(any(String.class));
    }

}