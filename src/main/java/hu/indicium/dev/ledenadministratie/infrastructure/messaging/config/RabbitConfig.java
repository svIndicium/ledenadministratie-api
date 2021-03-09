package hu.indicium.dev.ledenadministratie.infrastructure.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue paymentQueue() {
        return new Queue("payment");
    }

    @Bean
    Binding paymentBinding() {
        return BindingBuilder.bind(paymentQueue()).to(exchange()).with(paymentQueue().getName());
    }

    @Bean
    Queue notificationQueue() {
        return new Queue("notification");
    }

    @Bean
    Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(exchange()).with(notificationQueue().getName());
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("indicium");
    }
}