package hu.indicium.dev.ledenadministratie.mail.hooks;

import hu.indicium.dev.ledenadministratie.hooks.CreationHook;
import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Util;


public class MailListUserCreationHook implements CreationHook<UserDTO> {

    private MailListService mailListService;

    public MailListUserCreationHook(MailListService mailListService) {
        this.mailListService = mailListService;
    }

    @Override
    public void execute(UserDTO oldUser, UserDTO newUser) {
        mailListService.addUserToMailingList(new MailEntryDTO(newUser.getFirstName(), Util.getFullLastName(newUser.getMiddleName(), newUser.getLastName()), newUser.getEmail(), newUser.isToReceiveNewsletter()));
    }
}
