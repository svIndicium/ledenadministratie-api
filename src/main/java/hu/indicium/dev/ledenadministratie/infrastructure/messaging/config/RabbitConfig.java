package hu.indicium.dev.ledenadministratie.infrastructure.messaging.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queue() {
        return new Queue("payment");
    }

    @Bean
    Exchange exchange() {
        return new DirectExchange("indicium");
    }
}