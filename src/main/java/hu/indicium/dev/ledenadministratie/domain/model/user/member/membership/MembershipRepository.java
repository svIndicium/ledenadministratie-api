package hu.indicium.dev.ledenadministratie.domain.model.user.member.membership;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;

public interface MembershipRepository {
    MembershipId nextIdentity();

    Membership getMembershipByPaymentId(PaymentId paymentId);

    Membership save(Membership membership);
}
