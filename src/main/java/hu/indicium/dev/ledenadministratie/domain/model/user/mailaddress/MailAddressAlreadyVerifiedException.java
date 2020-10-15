package hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress;

import lombok.Getter;

import java.util.Date;

@Getter
public class MailAddressAlreadyVerifiedException extends RuntimeException {
    private final String address;

    private final Date verifiedAt;

    public MailAddressAlreadyVerifiedException(MailAddress mailAddress) {
        this.address = mailAddress.getAddress();
        this.verifiedAt = mailAddress.getVerifiedAt();
    }
}
