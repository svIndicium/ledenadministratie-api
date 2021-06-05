package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;

public interface MembershipService {
    void updateMembershipStatusWithPaymentId(PaymentId paymentId);
}
