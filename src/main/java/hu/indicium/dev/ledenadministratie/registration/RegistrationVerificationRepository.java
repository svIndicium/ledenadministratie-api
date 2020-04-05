package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import hu.indicium.dev.ledenadministratie.mail.MailObjectRepository;
import hu.indicium.dev.ledenadministratie.registration.events.RegistrationMailAddressVerified;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistrationVerificationRepository implements MailObjectRepository {

    private final RegistrationRepository registrationRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final RegistrationMapper registrationMapper;

    public RegistrationVerificationRepository(RegistrationRepository registrationRepository, ApplicationEventPublisher applicationEventPublisher, RegistrationMapper registrationMapper) {
        this.registrationRepository = registrationRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public int countByVerificationTokenAndVerificationTokenIsNotNull(String token) {
        return registrationRepository.countByVerificationTokenAndVerificationTokenIsNotNull(token);
    }

    @Override
    public Optional<MailObject> findByVerificationTokenAndVerificationTokenIsNotNull(String token) {
        return registrationRepository.findByVerificationTokenAndVerificationTokenIsNotNull(token);
    }

    @Override
    public boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress) {
        return registrationRepository.existsByMailAddressAndVerifiedAtIsNotNull(mailAddress);
    }

    @Override
    public MailObject save(MailObject mailObject) {
        return registrationRepository.save((Registration) mailObject);
    }

    @Override
    public void onVerify(MailObject mailObject) {
        RegistrationMailAddressVerified registrationMailAddressVerified = new RegistrationMailAddressVerified(this, registrationMapper.toDTO((Registration) mailObject));
        applicationEventPublisher.publishEvent(registrationMailAddressVerified);
    }
}
