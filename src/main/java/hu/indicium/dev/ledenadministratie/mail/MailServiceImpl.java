package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import hu.indicium.dev.ledenadministratie.registration.Registration;
import hu.indicium.dev.ledenadministratie.user.User;
import hu.indicium.dev.ledenadministratie.user.dto.MailDTO;
import hu.indicium.dev.ledenadministratie.util.Util;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;

    private final MailMapper mailMapper;

    private final TransactionalMailService transactionalMailService;

    public MailServiceImpl(MailRepository mailRepository, MailMapper mailMapper, TransactionalMailService transactionalMailService) {
        this.mailRepository = mailRepository;
        this.mailMapper = mailMapper;
        this.transactionalMailService = transactionalMailService;
    }

    @Override
    public MailDTO addMailAddress(MailEntryDTO mailEntryDTO) {
        isValidEmailAddress(mailEntryDTO.getEmail());
        if (mailRepository.existsByAddressAndVerifiedIsTrue(mailEntryDTO.getEmail().toLowerCase())) {
            throw new IllegalArgumentException("Email already in use");
        }
        Mail mail = new Mail(mailEntryDTO.getEmail().toLowerCase(), mailEntryDTO.isToReceiveNewsletter());
        mail = mailRepository.save(mail);
        return mailMapper.toDTO(mail);
    }

    @Override
    public void addUserToMailingList(MailEntryDTO mailEntryDTO) {
        this.mailListService.addUserToNewsLetter(mailEntryDTO);
    }

    @Override
    public void updateMailingListMember(MailEntryDTO oldEmail, MailEntryDTO newEmail) {
        this.mailListService.updateMailingListMember(oldEmail, newEmail);
    }

    @Override
    public void addUserToNewsLetter(MailEntryDTO mailEntryDTO) {
        this.mailListService.addUserToNewsLetter(mailEntryDTO);
    }

    @Override
    public void removeUserFromNewsLetter(MailEntryDTO mailEntryDTO) {
        this.mailListService.removeUserFromNewsLetter(mailEntryDTO);
    }

    @Override
    public void requestMailVerification(Long mailId) {
        if (mailRepository.existsByIdAndVerifiedIsTrue(mailId)) {
            throw new IllegalArgumentException("Email already verified");
        }
        Mail mail = mailRepository.findById(mailId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Email with id %d not found", mailId)));
        mail.setVerificationToken(this.generateVerificationToken());
        mail.setVerificationRequestedAt(new Date());
        mailRepository.save(mail);
        MailVerificationDTO mailVerificationDTO = new MailVerificationDTO();
        if (mail.getUser() != null) {
            User user = mail.getUser();
            mailVerificationDTO.setFirstName(user.getFirstName());
            mailVerificationDTO.setLastName(Util.getFullLastName(user.getMiddleName(), user.getLastName()));
        } else {
            Registration registration = mail.getRegistration();
            mailVerificationDTO.setFirstName(registration.getFirstName());
            mailVerificationDTO.setLastName(Util.getFullLastName(registration.getMiddleName(), registration.getLastName()));
        }
        mailVerificationDTO.setToken(mail.getVerificationToken());
        transactionalMailService.sendVerificationMail(mailVerificationDTO);
    }

    @Override
    public void verifyMail(String token) {
        this.transactionalMailService.verifyMail(token);
    }

    void isValidEmailAddress(String emailAddress) {
    }

    private String generateVerificationToken() {
        String token = Util.randomAlphaNumeric(10).toUpperCase();
        if (mailRepository.countByVerificationToken(token) != 0) {
            return generateVerificationToken();
        }
        return token;
    }
}
