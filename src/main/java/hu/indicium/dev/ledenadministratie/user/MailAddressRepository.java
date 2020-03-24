package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.mail.MailAbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailAddressRepository extends MailAbstractRepository<MailAddress, Long> {
    boolean existsByMailAddressAndVerifiedAtIsNotNull(String address);

    boolean existsByMailAddressAndUserId(String address, Long userId);

    boolean existsByIdAndVerifiedAtIsNotNull(Long mailId);

    int countByVerificationToken(String verificationToken);
}
