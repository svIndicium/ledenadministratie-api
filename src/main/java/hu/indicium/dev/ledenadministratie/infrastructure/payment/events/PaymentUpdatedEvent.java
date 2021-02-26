package hu.indicium.dev.ledenadministratie.infrastructure.payment.events;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PaymentUpdatedEvent {

    private String routingKey;

    private UUID paymentId;

    private int version;
}
