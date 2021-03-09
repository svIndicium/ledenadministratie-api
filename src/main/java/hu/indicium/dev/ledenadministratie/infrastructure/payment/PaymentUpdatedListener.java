package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.application.service.MembershipService;
import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.infrastructure.payment.events.PaymentUpdatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentUpdatedListener {

    private final ObjectMapper objectMapper;

    private final MembershipService membershipService;

    @RabbitListener(queues = "#{paymentQueue.name}")
    public void execute(String eventJson) {
        try {
            System.out.println(eventJson);
            PaymentUpdatedEvent payment = objectMapper.readerFor(PaymentUpdatedEvent.class).readValue(eventJson);
            PaymentId paymentId = PaymentId.fromId(payment.getPaymentId());
            membershipService.updateMembershipPaymentByPaymentId(paymentId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
