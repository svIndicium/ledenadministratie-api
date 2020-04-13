package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.mail.dto.TransactionalMailDTO;
import hu.indicium.dev.ledenadministratie.util.Util;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    private final TransactionalMailService transactionalMailService;

    private final MailObjectComponent mailObjectComponent;

    private final MailListService mailListService;

    public MailServiceImpl(TransactionalMailService transactionalMailService, MailObjectComponent mailObjectComponent, MailListService mailListService) {
        this.transactionalMailService = transactionalMailService;
        this.mailObjectComponent = mailObjectComponent;
        this.mailListService = mailListService;
    }

    @Override
    public MailObject sendVerificationMail(MailObject mailObject, TransactionalMailDTO transactionalMailDTO) {
        isValidEmailAddress(mailObject.getMailAddress());
        if (this.isMailAddressAlreadyVerified(mailObject.getMailAddress())) {
            throw new IllegalArgumentException("Address is already in use");
        }
        if (mailObject.getVerifiedAt() != null) {
            throw new IllegalStateException("Address is already verified");
        }
        mailObject.setVerificationRequestedAt(new Date());
        mailObject.setVerificationToken(generateVerificationToken());
        transactionalMailDTO.setMailAddress(mailObject.getMailAddress());
        transactionalMailDTO.set("token", mailObject.getVerificationToken());
        transactionalMailService.sendVerificationMail(transactionalMailDTO);
        return mailObject;
    }

    @Override
    public void sendPasswordResetMail(TransactionalMailDTO transactionalMailDTO) {
        this.transactionalMailService.sendPasswordResetMail(transactionalMailDTO);
    }

    @Override
    public void verifyMail(String mailAddress, String token) {
        MailObjectRepository repository = mailObjectComponent.getRepositoryFromToken(token);
        MailObject mailObject = repository.findByVerificationTokenAndVerificationTokenIsNotNull(token)
                .orElseThrow(() -> new IllegalStateException("Could not validate mail address"));
        if (mailObject.getVerifiedAt() != null) {
            throw new IllegalStateException("Could not validate mail address");
        }
        if (!mailObject.getMailAddress().equals(mailAddress)) {
            throw new IllegalStateException("Could not validate mail address");
        }
        mailObject.verify();
        mailObject = repository.save(mailObject);
        repository.onVerify(mailObject);
    }

    @Override
    public boolean isMailAddressAlreadyVerified(String mailAddress) {
        return mailObjectComponent.isMailAddressAlreadyVerified(mailAddress.toLowerCase());
    }

    @Override
    public void addMailAddressToMailingList(MailEntryDTO mailEntryDTO) {
        this.mailListService.addUserToMailingList(mailEntryDTO);
    }

    @Override
    public void addMailAddressToNewsletter(MailEntryDTO mailEntryDTO) {
        this.mailListService.addUserToNewsLetter(mailEntryDTO);
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
