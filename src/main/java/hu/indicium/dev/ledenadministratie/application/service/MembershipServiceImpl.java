package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipRepository;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.Payment;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    private final PaymentService paymentService;

    @Override
    public void updateMembershipStatusWithPaymentId(PaymentId paymentId) {
        Payment payment = paymentService.getPaymentByPaymentId(paymentId);

        Membership membership = membershipRepository.getMembershipByPaymentId(paymentId);

        log.info("Received update for payment " + paymentId.getId().toString() + ". Status was " + payment.getStatus().toString());
        if (payment.getStatus() == PaymentStatus.PAID) {
            membership.activate();
        } else {
            log.info("Received update for payment " + paymentId.getId().toString() + ". Status was " + payment.getStatus().toString());
        }

        membershipRepository.save(membership);
    }
}
