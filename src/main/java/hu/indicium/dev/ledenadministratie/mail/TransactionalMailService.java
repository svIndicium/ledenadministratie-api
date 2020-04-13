package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.TransactionalMailDTO;

public interface TransactionalMailService {
    void sendVerificationMail(TransactionalMailDTO transactionalMailDTO);

    void sendPasswordResetMail(TransactionalMailDTO transactionalMailDTO);
}
