package hu.indicium.dev.ledenadministratie.registration.validation;

import hu.indicium.dev.ledenadministratie.registration.Registration;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Registration Comment validator")
class CommentValidatorTest {

    private Validator<Registration> registrationValidator = new CommentValidator();

    private Registration registration;

    @BeforeEach
    void setUp() {
        registration = new Registration();
        registration.setId(1L);
        registration.setFirstName("John");
        registration.setLastName("Doe");
        registration.setEmail("John@doe.com");
        registration.setPhoneNumber("+31612345678");
        registration.setDateOfBirth(new Date());
        registration.setStudyType(new StudyType("SD"));
    }

    @Test
    @DisplayName("Validate not finalized registration")
    void shouldThrowNoException_whenValidate_ifNotFinalized() {
        registration.setFinalizedAt(null);
        try {
            registrationValidator.validate(registration);
        } catch (IllegalArgumentException e) {
            fail("User is not finalized, so no comment is required!");
        }
    }

    @Test
    @DisplayName("Validate approved registration")
    void shouldThrowNoException_whenValidate_ifApproved() {
        registration.setFinalizedAt(new Date());
        registration.setApproved(true);
        registration.setFinalizedBy("Alex");
        try {
            registrationValidator.validate(registration);
        } catch (IllegalArgumentException e) {
            fail("User is approved, so no comment is required!");
        }
    }

    @Test
    @DisplayName("Validate denied registration without comment")
    void shouldThrowException_whenValidate_ifDeniedWithoutComment() {
        registration.setFinalizedAt(new Date());
        registration.setApproved(false);
        registration.setFinalizedBy("Alex");
        try {
            registrationValidator.validate(registration);
            fail("Should throw exception because comment is required if not approved");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(CommentValidator.ERROR_MESSAGE);
        }
    }

    @Test
    @DisplayName("Validate denied registration with blank comment")
    void shouldThrowException_whenValidate_ifDeniedWithBlankComment() {
        registration.setFinalizedAt(new Date());
        registration.setApproved(false);
        registration.setFinalizedBy("Alex");
        registration.setComment("");
        try {
            registrationValidator.validate(registration);
            fail("Should throw exception because comment is required not to be blank if not approved");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(CommentValidator.ERROR_MESSAGE);
        }
    }

    @Test
    @DisplayName("Validate denied registration")
    void shouldThrowNoException_whenValidate() {
        registration.setFinalizedAt(new Date());
        registration.setApproved(false);
        registration.setFinalizedBy("Alex");
        registration.setComment("Troll");
        try {
            registrationValidator.validate(registration);
        } catch (IllegalArgumentException e) {
            fail("Should not throw exception because comment is provided!");
        }
    }


}