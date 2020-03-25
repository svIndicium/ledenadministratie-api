package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import hu.indicium.dev.ledenadministratie.util.Util;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    private final TransactionalMailService transactionalMailService;

    private final MailObjectComponent mailObjectComponent;

    public MailServiceImpl(TransactionalMailService transactionalMailService, MailObjectComponent mailObjectComponent) {
        this.transactionalMailService = transactionalMailService;
        this.mailObjectComponent = mailObjectComponent;
    }

    @Override
    public MailObject sendVerificationMail(MailObject mailObject, MailVerificationDTO mailVerificationDTO) {
        isValidEmailAddress(mailObject.getMailAddress());
        if (this.isMailAddressAlreadyVerified(mailObject.getMailAddress())) {
            throw new IllegalArgumentException("Address is already in use");
        }
        if (mailObject.getVerifiedAt() != null) {
            throw new IllegalStateException("Address is already verified");
        }
        mailObject.setVerificationRequestedAt(new Date());
        mailObject.setVerificationToken(generateVerificationToken());
        mailVerificationDTO.setMailAddress(mailObject.getMailAddress());
        mailVerificationDTO.setToken(mailObject.getVerificationToken());
        transactionalMailService.sendVerificationMail(mailVerificationDTO);
        return mailObject;
    }

    @Override
    public void verifyMail(String mailAddress, String token) {
        MailObjectRepository repository = mailObjectComponent.getRepositoryFromToken(token);
        MailObject mailObject = repository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalStateException("Could not validate mail address"));
        if (mailObject.getVerifiedAt() != null) {
            throw new IllegalStateException("Could not validate mail address");
        }
        if (!mailObject.getMailAddress().equals(mailAddress)) {
            throw new IllegalStateException("Could not validate mail address");
        }
        mailObject.verify();
        repository.save(mailObject);
    }

    @Override
    public boolean isMailAddressAlreadyVerified(String mailAddress) {
        return mailObjectComponent.isMailAddressAlreadyVerified(mailAddress.toLowerCase());
    }

    void isValidEmailAddress(String emailAddress) {
    }

    private String generateVerificationToken() {
        String token = Util.randomAlphaNumeric(10).toUpperCase();
        if (mailObjectComponent.countByVerificationToken(token) != 0) {
            return generateVerificationToken();
        }
        return token;
    }
}
