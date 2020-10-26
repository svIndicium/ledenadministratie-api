package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class MailAddressDto {
    private UUID id;

    private String address;

    private Date verificationRequestedAt;

    private Date verifiedAt;

    private boolean receivesNewsletter;

    private Date createdAt;

    private Date updatedAt;

    public MailAddressDto(MailAddress mailAddress) {
        this.id = mailAddress.getId();
        this.address = mailAddress.getAddress();
        this.verificationRequestedAt = mailAddress.getVerificationRequestedAt();
        this.verifiedAt = mailAddress.getVerifiedAt();
        this.receivesNewsletter = mailAddress.isReceivesNewsletter();
        this.createdAt = mailAddress.getCreatedAt();
        this.updatedAt = mailAddress.getUpdatedAt();
    }
}
