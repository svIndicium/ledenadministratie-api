package hu.indicium.dev.ledenadministratie.registration.listeners;

import hu.indicium.dev.ledenadministratie.mail.MailService;
import hu.indicium.dev.ledenadministratie.mail.dto.TransactionalMailDTO;
import hu.indicium.dev.ledenadministratie.registration.RegistrationMapper;
import hu.indicium.dev.ledenadministratie.registration.events.NewRegistrationAdded;
import hu.indicium.dev.ledenadministratie.util.Util;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendVerificationMailOnNewRegistration implements ApplicationListener<NewRegistrationAdded> {

    private final MailService mailService;

    public SendVerificationMailOnNewRegistration(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(NewRegistrationAdded event) {
        TransactionalMailDTO transactionalMailDTO = new TransactionalMailDTO(event.getRegistration().getFirstName(), Util.getFullLastName(event.getRegistration().getMiddleName(), event.getRegistration().getLastName()));
        mailService.sendVerificationMail(RegistrationMapper.map(event.getRegistration()), transactionalMailDTO);
    }
}
