package hu.indicium.dev.ledenadministratie.mail;

import java.util.Optional;

public interface MailObjectRepository {
    int countByVerificationTokenAndVerificationTokenIsNotNull(String token);

    Optional<MailObject> findByVerificationTokenAndVerificationTokenIsNotNull(String token);

    boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress);

    MailObject save(MailObject mailObject);

    void onVerify(MailObject mailObject);
}
