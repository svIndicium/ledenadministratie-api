package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;

public interface PaymentService {
    PaymentId createContributionPayment(Membership membership);

    Payment getPaymentByPaymentId(PaymentId paymentId);
}
