package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import hu.indicium.dev.ledenadministratie.mail.MailObjectRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MailAddressVerificationRepository implements MailObjectRepository {

    private final MailAddressRepository mailAddressRepository;

    public MailAddressVerificationRepository(MailAddressRepository mailAddressRepository) {
        this.mailAddressRepository = mailAddressRepository;
    }

    @Override
    public int countByVerificationTokenAndVerificationTokenIsNotNull(String token) {
        return mailAddressRepository.countByVerificationTokenAndVerificationTokenIsNotNull(token);
    }

    @Override
    public Optional<MailObject> findByVerificationTokenAndVerificationTokenIsNotNull(String token) {
        return mailAddressRepository.findByVerificationTokenAndVerificationTokenIsNotNull(token);
    }

    @Override
    public boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress) {
        return mailAddressRepository.existsByMailAddressAndVerifiedAtIsNotNull(mailAddress);
    }

    @Override
    public MailObject save(MailObject mailObject) {
        return mailAddressRepository.save((MailAddress) mailObject);
    }
}
