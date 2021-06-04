package hu.indicium.dev.ledenadministratie.infrastructure.payment;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponse {
    private Payment data;
    private Object error;
    private int status;
    private Date timestamp;
}
