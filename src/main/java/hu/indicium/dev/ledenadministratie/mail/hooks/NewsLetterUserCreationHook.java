package hu.indicium.dev.ledenadministratie.mail.hooks;

import hu.indicium.dev.ledenadministratie.hooks.CreationHook;
import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;

public class NewsLetterUserCreationHook implements CreationHook<UserDTO> {

    private MailListService mailListService;

    public NewsLetterUserCreationHook(MailListService mailListService) {
        this.mailListService = mailListService;
    }

    @Override
    public void execute(UserDTO oldUser, UserDTO newUser) {
//        try {
//            if (newUser.isToReceiveNewsletter()) {
//                mailListService.addUserToNewsLetter(new MailEntryDTO(newUser.getFirstName(), Util.getFullLastName(newUser.getMiddleName(), newUser.getLastName()), newUser.getEmail(), newUser.isToReceiveNewsletter()));
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
}
