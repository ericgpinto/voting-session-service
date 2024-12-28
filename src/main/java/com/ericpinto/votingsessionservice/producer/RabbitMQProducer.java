package com.ericpinto.votingsessionservice.producer;

import com.ericpinto.votingsessionservice.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Object message) {
        log.info("Sending message: {}", message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_VOTING_SESSION, "routing_key", message);
        log.info("Sent message: {}", message);
    }
}
