package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipRepository;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.Payment;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    private final PaymentService paymentService;

    @Override
    public void updateMembershipPaymentByPaymentId(PaymentId paymentId) {
        Payment payment = paymentService.getPaymentByPaymentId(paymentId);

        Membership membership = membershipRepository.getMembershipByPaymentId(paymentId);

        membership.setPaymentStatus(payment.getStatus());

        membershipRepository.save(membership);
    }
}
