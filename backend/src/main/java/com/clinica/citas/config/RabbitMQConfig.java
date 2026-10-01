package com.clinica.citas.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_RECORDATORIOS = "recordatorios.envio";
    public static final String EXCHANGE_NOTIFICATIONS = "notifications.direct";
    public static final String ROUTING_KEY_RECORDATORIO = "recordatorio.send";

    @Bean
    public Queue recordatoriosQueue() {
        return QueueBuilder.durable(QUEUE_RECORDATORIOS)
                .withArgument("x-dead-letter-exchange", "dlx.recordatorios")
                .withArgument("x-dead-letter-routing-key", "recordatorios.failed")
                .build();
    }

    @Bean
    public DirectExchange notificationsExchange() {
        return new DirectExchange(EXCHANGE_NOTIFICATIONS);
    }

    @Bean
    public Binding bindingRecordatorios(Queue recordatoriosQueue, DirectExchange notificationsExchange) {
        return BindingBuilder.bind(recordatoriosQueue).to(notificationsExchange).with(ROUTING_KEY_RECORDATORIO);
    }
}
