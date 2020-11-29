package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class MemberDto {
    private String memberId;

    private MemberDetailsDto memberDetails;

    private ReviewDetailsDto reviewDetails;

    private Collection<MailAddressDto> mailAddresses;

    public MemberDto(Member member) {
        this.memberId = member.getMemberId().getAuthId();
        this.memberDetails = new MemberDetailsDto(member.getMemberDetails());
        this.reviewDetails = new ReviewDetailsDto(member.getReviewDetails());
        this.mailAddresses = new HashSet<>();
        for (MailAddress mailAddress : member.getMailAddresses()) {
            this.mailAddresses.add(new MailAddressDto(mailAddress));
        }
    }
}
