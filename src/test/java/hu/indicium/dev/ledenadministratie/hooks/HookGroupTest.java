package hu.indicium.dev.ledenadministratie.hooks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@DisplayName("Hook Group")
class HookGroupTest {

    @Mock
    Hook<String> validator;

    @Mock
    Hook<String> validator1;

    @Test
    @DisplayName("Run all attached hooks")
    void shouldRunAllHooksAttached() {
        HookGroup<String> validatorGroup = new HookGroup<>(Arrays.asList(validator, validator1));
        validatorGroup.execute("testString", "testString");

        verify(validator).execute(any(String.class), any(String.class));
        verify(validator1).execute(any(String.class), any(String.class));
    }

}