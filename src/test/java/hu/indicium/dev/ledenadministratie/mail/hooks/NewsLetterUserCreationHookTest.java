package hu.indicium.dev.ledenadministratie.mail.hooks;

import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@DisplayName("Newsletter creation hook")
class NewsLetterUserCreationHookTest {

    @Test
    @DisplayName("Execute hook if newsletter is to be received")
    void shouldAddUserToNewsletterList_ifNewsletterIsToBeReceived() {

        MailListService mailListService = mock(MailListService.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setMiddleName(null);
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@doe.com");
        userDTO.setToReceiveNewsletter(true);

        MailEntryDTO mailEntryDTO = new MailEntryDTO(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.isToReceiveNewsletter());

        NewsLetterUserCreationHook newsLetterUserCreationHook = new NewsLetterUserCreationHook(mailListService);

        newsLetterUserCreationHook.execute(null, userDTO);

        verify(mailListService, times(1)).addUserToNewsLetter(refEq(mailEntryDTO));
    }

    @Test
    @DisplayName("Don't execute hook if newsletter is not to be received")
    void shouldNotAddUserToNewsletterList_ifNewsletterIsNotToBeReceived() {

        MailListService mailListService = mock(MailListService.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setMiddleName(null);
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@doe.com");
        userDTO.setToReceiveNewsletter(false);

        MailEntryDTO mailEntryDTO = new MailEntryDTO(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.isToReceiveNewsletter());

        NewsLetterUserCreationHook newsLetterUserCreationHook = new NewsLetterUserCreationHook(mailListService);

        newsLetterUserCreationHook.execute(null, userDTO);

        verify(mailListService, never()).addUserToNewsLetter(refEq(mailEntryDTO));
    }

}