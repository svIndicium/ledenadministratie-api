package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentRequest {
    private String description;

    private double amount;

    private String authId;
}
