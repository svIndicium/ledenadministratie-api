package hu.indicium.dev.ledenadministratie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.CreatePaymentRequest;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.PaymentResponse;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.PaymentService;
import hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa.MembershipJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@SpringBootApplication
public class LedenadministratieApplication implements CommandLineRunner {

    @Autowired
    private MembershipJpaRepository membershipJpaRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WebClient webClient;

    @Value("${hu.indicium.api.payments.url}")
    private String paymentUrl;

    public static void main(String[] args) {
        SpringApplication.run(LedenadministratieApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("transfer", 15.00);
        for (Membership membership : membershipJpaRepository.getMembershipsByPaymentIdIsNull()) {
            PaymentId paymentId = paymentService.createContributionPayment(membership);
            membership.assignPayment(paymentId);
            membershipJpaRepository.save(membership);
            webClient.post()
                    .uri(paymentUrl + "/payments/" + paymentId.getId().toString() + "/transactions")
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .body(Mono.just(createTransferRequest), CreateTransferRequest.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }
}
