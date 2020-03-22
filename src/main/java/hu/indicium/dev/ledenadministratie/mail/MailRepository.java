package hu.indicium.dev.ledenadministratie.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    boolean existsByAddressAndVerifiedIsTrue(String address);

    boolean existsByIdAndVerifiedIsTrue(Long mailId);

    int countByVerificationToken(String verificationToken);
}
