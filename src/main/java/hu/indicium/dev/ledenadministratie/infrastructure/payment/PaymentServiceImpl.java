package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.util.Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${hu.indicium.api.payments.url}")
    private String paymentUrl;

    private final WebClient webClient;

    @Override
    public PaymentId createContributionPayment(Member member) {
        CreatePaymentRequest createPaymentRequest = CreatePaymentRequest.builder()
                .amount(15)
                .authId(member.getMemberId().getAuthId())
                .description("Contributie 2021-2022")
                .build();
        Payment payment = webClient.post()
                .uri(paymentUrl + "/payments")
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

    @Override
    public Payment getPaymentByPaymentId(PaymentId paymentId) {
        PaymentResponse paymentResponse = webClient.get()
                .uri(paymentUrl + "/payments/" + paymentId.getId().toString())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();
        if (paymentResponse == null || paymentResponse.getData() == null) {
            throw new RuntimeException("Could not get payment");
        }
        return paymentResponse.getData();
    }
}
