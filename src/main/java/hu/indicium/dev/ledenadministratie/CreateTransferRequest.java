package hu.indicium.dev.ledenadministratie;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTransferRequest {
    private String method;

    private Double amount;
}
