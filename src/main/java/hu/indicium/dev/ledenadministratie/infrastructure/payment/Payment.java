package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Payment {
    private UUID id;

    private String memberId;

    private double amount;

    private double openAmount;

    private double remainingAmount;

    private PaymentStatus status;

    private String description;

    private Date createdAt;
}
