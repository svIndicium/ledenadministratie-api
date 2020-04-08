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

    public static MailAddress map(MailAddressDTO mailAddressDTO) {
        MailAddress mailAddress = new MailAddress();
        mailAddress.setId(mailAddressDTO.getId());
        mailAddress.setMailAddress(mailAddressDTO.getAddress());
        mailAddress.setReceivesNewsletter(mailAddressDTO.isReceivesNewsletter());
        mailAddress.setVerificationRequestedAt(mailAddressDTO.getVerificationRequestedAt());
        return mailAddress;
    }
}
