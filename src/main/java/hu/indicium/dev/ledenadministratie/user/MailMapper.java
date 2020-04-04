package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;

public class MailMapper {
    public static MailAddressDTO map(MailAddress mailAddress) {
        MailAddressDTO mailAddressDTO = new MailAddressDTO();
        mailAddressDTO.setAddress(mailAddress.getMailAddress());
        mailAddressDTO.setId(mailAddress.getId());
        mailAddressDTO.setCreatedAt(mailAddress.getCreatedAt());
        mailAddressDTO.setVerified(mailAddress.getVerifiedAt() != null);
        mailAddressDTO.setVerifiedAt(mailAddress.getVerifiedAt());
        mailAddressDTO.setVerificationRequestedAt(mailAddress.getVerificationRequestedAt());
        return mailAddressDTO;
    }
}
