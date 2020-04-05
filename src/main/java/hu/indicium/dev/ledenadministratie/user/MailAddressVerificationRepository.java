package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import hu.indicium.dev.ledenadministratie.mail.MailObjectRepository;
import hu.indicium.dev.ledenadministratie.user.events.MailAddressVerified;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MailAddressVerificationRepository implements MailObjectRepository {

    private final MailAddressRepository mailAddressRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public MailAddressVerificationRepository(MailAddressRepository mailAddressRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.mailAddressRepository = mailAddressRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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

    @Override
    public void onVerify(MailObject mailObject) {
        MailAddressVerified mailAddressVerified = new MailAddressVerified(this, MailMapper.map((MailAddress) mailObject));
        applicationEventPublisher.publishEvent(mailAddressVerified);
    }
}
