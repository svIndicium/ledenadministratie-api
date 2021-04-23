package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class MembershipDto {
    private UUID id;

    private Date startDate;

    private Date endDate;

    private UUID paymentId;

    private PaymentStatus paymentStatus;

    public MembershipDto(Membership membership) {
        this.id = membership.getMembershipId().getId();
        this.startDate = membership.getStartDate();
        this.endDate = membership.getEndDate();
        this.paymentId = membership.getPaymentId().getId();
        this.paymentStatus = membership.getPaymentStatus();
    }
}
