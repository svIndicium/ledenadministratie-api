package hu.indicium.dev.ledenadministratie.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Name")
class NameTest {

    @Test
    @DisplayName("Create name")
    void createName() {
        String firstName = "Miguel";
        String middleName = "Don";
        String lastName = "Gomez";

        Name name = new Name(firstName, middleName, lastName);

        assertThat(name).isNotNull();
        assertThat(name.getFirstName()).isEqualTo(firstName);
        assertThat(name.getMiddleName()).isEqualTo(middleName);
        assertThat(name.getLastName()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("Get last name")
    void checkIfLastName_isMiddleNameAppendedToLastName() {
        String firstName = "Miguel";
        String middleName = "Don";
        String lastName = "Gomez";

        Name name = new Name(firstName, middleName, lastName);

        assertThat(name.getFullLastName()).isEqualTo("Don Gomez");
        assertThat(name.getFullName()).isEqualTo("Miguel Don Gomez");
    }
}