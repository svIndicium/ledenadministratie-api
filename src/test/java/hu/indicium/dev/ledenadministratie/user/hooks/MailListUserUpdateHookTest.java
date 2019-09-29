package hu.indicium.dev.ledenadministratie.user.hooks;

import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@DisplayName("Mail list update hook")
class MailListUserUpdateHookTest {

    private MailListService mailListService = mock(MailListService.class);

    private MailListUserUpdateHook mailListUserUpdateHook = new MailListUserUpdateHook(mailListService);

    private UserDTO oldUserDTO = getDefault();

    private MailEntryDTO oldMailEntryDTO = toMailEntryDTO(oldUserDTO);

    @Test
    @DisplayName("Execute hook")
    void shouldExecuteHook_whenUsersAreDifferent() {

        UserDTO newUserDTO = getDefault();

        newUserDTO.setFirstName("Johnson");

        check(newUserDTO);

        newUserDTO = getDefault();

        newUserDTO.setLastName("Smith");

        check(newUserDTO);

        newUserDTO = getDefault();

        newUserDTO.setEmail("Johnson@smith.com");

        check(newUserDTO);

        newUserDTO = getDefault();

        newUserDTO.setToReceiveNewsletter(false);

        check(newUserDTO);

        newUserDTO = getDefault();

        mailListUserUpdateHook.execute(oldUserDTO, newUserDTO);

        verify(mailListService, never()).updateMailingListMember(refEq(oldMailEntryDTO), refEq(toMailEntryDTO(newUserDTO)));
    }

    private void check(UserDTO newUserDTO) {
        mailListUserUpdateHook.execute(oldUserDTO, newUserDTO);

        verify(mailListService, times(1)).updateMailingListMember(refEq(oldMailEntryDTO), refEq(toMailEntryDTO(newUserDTO)));
    }

    private MailEntryDTO toMailEntryDTO(UserDTO userDTO) {
        return new MailEntryDTO(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.isToReceiveNewsletter());
    }

    private UserDTO getDefault() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setFirstName("Johns");
        newUserDTO.setMiddleName(null);
        newUserDTO.setLastName("Doe");
        newUserDTO.setEmail("john@doe.com");
        newUserDTO.setToReceiveNewsletter(true);
        newUserDTO.setDateOfBirth(new Date());
        return newUserDTO;
    }
}