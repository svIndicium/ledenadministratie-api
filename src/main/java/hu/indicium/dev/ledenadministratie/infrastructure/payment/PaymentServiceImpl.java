package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
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
    public PaymentId createContributionPayment(Membership membership) {
        CreatePaymentRequest createPaymentRequest = CreatePaymentRequest.builder()
                .amount(15)
                .authId(membership.getMember().getMemberId().getAuthId())
                .description(getPaymentDescription(membership))
                .build();
        PaymentResponse paymentResponse = webClient.post()
                .uri(paymentUrl + "/payments")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(createPaymentRequest), CreatePaymentRequest.class)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();
        if (paymentResponse == null || paymentResponse.getData() == null) {
            throw new RuntimeException("Could not create new payment");
        }
        return PaymentId.fromId(paymentResponse.getData().getId());
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

    private String getPaymentDescription(Membership membership) {
        return "Contributie " + membership.getStartYear() + "-" + membership.getEndYear();
    }
}
