package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@Getter
@Setter
public class MemberDTO {
    private String memberId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String phoneNumber;

    private Date dateOfBirth;

    private Collection<MailAddressDTO> mailAddresses;

    private UUID studyTypeId;

    private Date reviewedAt;

    private String reviewedBy;

    public MemberDTO(Member member) {
        this.memberId = member.getMemberId().getAuthId();
        this.firstName = member.getMemberDetails().getName().getFirstName();
        this.middleName = member.getMemberDetails().getName().getMiddleName();
        this.lastName = member.getMemberDetails().getName().getLastName();
        this.phoneNumber = member.getMemberDetails().getPhoneNumber();
        this.dateOfBirth = member.getMemberDetails().getDateOfBirth();
        this.studyTypeId = member.getMemberDetails().getStudyType().getStudyTypeId().getId();
        this.reviewedAt = member.getReviewDetails().getReviewedAt();
        this.reviewedBy = member.getReviewDetails().getReviewedBy();
        this.mailAddresses = new HashSet<>();
        for (MailAddress mailAddress : member.getMailAddresses()) {
            this.mailAddresses.add(new MailAddressDTO(mailAddress));
        }
    }
}
