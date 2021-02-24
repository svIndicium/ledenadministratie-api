package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WebClient webClient;

    @Override
    public PaymentId createContributionPayment(Member member) {
        CreatePaymentRequest createPaymentRequest = CreatePaymentRequest.builder()
                .amount(15)
                .authId(member.getMemberId().getAuthId())
                .description("Contributie 2021-2022")
                .build();
        Payment payment = webClient.post()
                .uri("/payments")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(createPaymentRequest), CreatePaymentRequest.class)
                .retrieve()
                .bodyToMono(Payment.class)
                .block();
        if (payment == null) {
            throw new RuntimeException("Could not create new payment");
        }
        return PaymentId.fromId(payment.getId());
    }
}
