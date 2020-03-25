//package hu.indicium.dev.ledenadministratie.user.validation;
//
//import hu.indicium.dev.ledenadministratie.studytype.StudyType;
//import hu.indicium.dev.ledenadministratie.user.User;
//import hu.indicium.dev.ledenadministratie.util.Validator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//@DisplayName("User email validator")
//class EmailValidatorTest {
//
//    private Validator<User> emailValidator = new EmailValidator();
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setId(1L);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setPhoneNumber("+31612345678");
//        user.setDateOfBirth(new Date());
//        user.setStudyType(new StudyType());
//    }
//
//    @Test
//    @DisplayName("Validate")
//    void validate() {
//        user.setEmail("john@doe.com");
//        emailValidator.validate(user);
//        assertTrue(true);
//    }
//
//    @Test
//    @DisplayName("Validate empty email address")
//    void validateEmptyEmailShouldThrowException() {
//        user.setEmail("");
//        try {
//            emailValidator.validate(user);
//            fail();
//        } catch (Exception e) {
//            assertThat(e.getMessage()).isEqualTo(EmailValidator.EMPTY_EMAIL_ERROR_MESSAGE);
//        }
//    }
//
//    @Test
//    @DisplayName("Validate email address without apenstaartje")
//    void validateEmailWithoutApenstaartjeShouldThrowException() {
//        user.setEmail("johndoe.com");
//        try {
//            emailValidator.validate(user);
//            fail();
//        } catch (Exception e) {
//            assertThat(e.getMessage()).isEqualTo(EmailValidator.EMAIL_INCORRECT);
//        }
//    }
//}