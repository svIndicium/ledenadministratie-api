package hu.indicium.dev.ledenadministratie.mail.hooks;

import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@DisplayName("Mail list user creation hook")
class MailListUserCreationHookTest {

    @Test
    @DisplayName("Execute hook")
    void shouldCallMailListService_whenExecuteHook() {

        MailListService mailListService = mock(MailListService.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setMiddleName(null);
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@doe.com");
        userDTO.setToReceiveNewsletter(true);

        MailEntryDTO mailEntryDTO = new MailEntryDTO(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.isToReceiveNewsletter());

        MailListUserCreationHook mailListUserCreationHook = new MailListUserCreationHook(mailListService);

        mailListUserCreationHook.execute(null, userDTO);

        verify(mailListService, times(1)).addUserToMailingList(refEq(mailEntryDTO));
    }

}