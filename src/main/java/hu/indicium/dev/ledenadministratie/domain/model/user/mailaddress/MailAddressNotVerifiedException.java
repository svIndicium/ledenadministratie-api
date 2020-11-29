package hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress;

import lombok.Getter;

import java.util.Date;

@Getter
public class MailAddressNotVerifiedException extends RuntimeException {
    private final String mailAddress;

    private final Date verificationRequestedAt;

    public MailAddressNotVerifiedException(MailAddress mailAddress) {
        this.mailAddress = mailAddress.getAddress();
        this.verificationRequestedAt = mailAddress.getVerificationRequestedAt();
    }
}
