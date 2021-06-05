package hu.indicium.dev.ledenadministratie.domain.model.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
public class PaymentId implements Serializable {
    @Column(name = "payment_id")
    private UUID id;

    private PaymentId(UUID id) {
        this.id = id;
    }

    public static PaymentId fromId(UUID id) {
        return new PaymentId(id);
    }
}
