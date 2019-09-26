package hu.indicium.dev.ledenadministratie.user.hooks;

import hu.indicium.dev.ledenadministratie.hooks.UpdateHook;
import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Util;

public class MailListUserUpdateHook implements UpdateHook<UserDTO> {

    private final MailListService mailListService;

    public MailListUserUpdateHook(MailListService mailListService) {
        this.mailListService = mailListService;
    }

    @Override
    public void execute(UserDTO oldEntity, UserDTO newEntity) {
        if (!oldEntity.getFirstName().equals(newEntity.getFirstName()) || !Util.getFullLastName(oldEntity.getMiddleName(), oldEntity.getLastName()).equals(Util.getFullLastName(newEntity.getMiddleName(), newEntity.getLastName())) || !oldEntity.getEmail().equals(newEntity.getEmail()) || oldEntity.isToReceiveNewsletter() != newEntity.isToReceiveNewsletter()) {
            mailListService.updateMailingListMember(new MailEntryDTO(oldEntity.getFirstName(), Util.getFullLastName(oldEntity.getMiddleName(), oldEntity.getLastName()), oldEntity.getEmail(), oldEntity.isToReceiveNewsletter()), new MailEntryDTO(newEntity.getFirstName(), Util.getFullLastName(newEntity.getMiddleName(), newEntity.getLastName()), newEntity.getEmail(), newEntity.isToReceiveNewsletter()));
        }
    }
}
