package hu.indicium.dev.ledenadministratie.domain.model.user.member.membership;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;

import java.util.Collection;

public interface MembershipRepository {
    MembershipId nextIdentity();

    Collection<Membership> getMembershipsByMemberId(MemberId memberId);

    Membership getMembershipByPaymentId(PaymentId paymentId);

    Membership save(Membership membership);
}
