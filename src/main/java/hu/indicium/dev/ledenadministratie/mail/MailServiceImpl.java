package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import hu.indicium.dev.ledenadministratie.util.Util;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    private final TransactionalMailService transactionalMailService;

    private final MailAbstractComponent mailAbstractComponent;

    public MailServiceImpl(TransactionalMailService transactionalMailService, MailAbstractComponent mailAbstractComponent) {
        this.transactionalMailService = transactionalMailService;
        this.mailAbstractComponent = mailAbstractComponent;
    }

    @Override
    public MailAbstract sendVerificationMail(MailAbstract mailAbstract, MailVerificationDTO mailVerificationDTO) {
        if (mailAbstract.getVerifiedAt() != null) {
            throw new IllegalStateException("Address is already verified");
        }
        isValidEmailAddress(mailAbstract.getMailAddress());
        mailAbstract.setVerificationRequestedAt(new Date());
        mailAbstract.setVerificationToken(generateVerificationToken());
        mailVerificationDTO.setMailAddress(mailAbstract.getMailAddress());
        mailVerificationDTO.setToken(mailAbstract.getVerificationToken());
        transactionalMailService.sendVerificationMail(mailVerificationDTO);
        return mailAbstract;
    }

    @Override
    public void verifyMail(String mailAddress, String token) {
        MailAbstractRepository<? extends MailAbstract> repository = mailAbstractComponent.getRepositoryFromToken(token);
        MailAbstract mailAbstract = repository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalStateException("Could not validate mail address"));
        if (mailAbstract.getVerifiedAt() != null) {
            throw new IllegalStateException("Could not validate mail address");
        }
        if (!mailAbstract.getMailAddress().equals(mailAddress)) {
            throw new IllegalStateException("Could not validate mail address");
        }
        mailAbstract.setVerifiedAt(new Date());
        repository.save(mailAbstract);
    }

    @Override
    public boolean isMailAddressAlreadyVerified(String mailAddress) {
        return mailAbstractComponent.isMailAddressAlreadyVerified(mailAddress.toLowerCase());
    }

    void isValidEmailAddress(String emailAddress) {
    }

    private String generateVerificationToken() {
        String token = Util.randomAlphaNumeric(10).toUpperCase();
        if (mailAbstractComponent.countByVerificationToken(token) != 0) {
            return generateVerificationToken();
        }
        return token;
    }
}
