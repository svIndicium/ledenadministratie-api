package hu.indicium.dev.ledenadministratie.mail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailAbstractRepository<T, ID> extends JpaRepository<T, ID> {
    int countByVerificationToken(String token);

    Optional<T> findByVerificationToken(String token);
}
