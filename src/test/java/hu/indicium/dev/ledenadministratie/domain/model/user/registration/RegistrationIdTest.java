package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Registration Id")
class RegistrationIdTest {
    @Test
    @DisplayName("Create registration id")
    void createNewRegistrationId() {
        UUID id = UUID.randomUUID();
        RegistrationId registrationId = RegistrationId.fromId(id);

        assertThat(registrationId).isNotNull();
        assertThat(registrationId.getId()).isEqualTo(id);
    }
}