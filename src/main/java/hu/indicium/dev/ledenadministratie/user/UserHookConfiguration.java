package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.hooks.CreationHook;
import hu.indicium.dev.ledenadministratie.hooks.HookGroup;
import hu.indicium.dev.ledenadministratie.hooks.UpdateHook;
import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.mail.hooks.MailListUserCreationHook;
import hu.indicium.dev.ledenadministratie.mail.hooks.MailListUserUpdateHook;
import hu.indicium.dev.ledenadministratie.mail.hooks.NewsLetterUserCreationHook;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class UserHookConfiguration {

    private final MailListService mailListService;

    public UserHookConfiguration(MailListService mailListService) {
        this.mailListService = mailListService;
    }

    @Bean
    public CreationHook<UserDTO> userCreationHook() {
        return new HookGroup<>(Arrays.asList(new MailListUserCreationHook(mailListService), new NewsLetterUserCreationHook(mailListService)));
    }

    @Bean
    public UpdateHook<UserDTO> userUpdateHook() {
        return new HookGroup<>(Collections.singletonList(new MailListUserUpdateHook(mailListService)));
    }
}
