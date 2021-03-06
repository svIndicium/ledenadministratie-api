package hu.indicium.dev.ledenadministratie.infrastructure.messaging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitEventService implements EventService {

    private final RabbitTemplate rabbitTemplate;

    private final Exchange exchange;

    private final ObjectMapper objectMapper;

    @Override
    public void emitEvent(Event event) {
        try {
            String res = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(exchange.getName(), event.getRoutingKey(), res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
