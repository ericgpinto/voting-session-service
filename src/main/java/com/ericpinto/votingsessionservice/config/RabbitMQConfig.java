package com.ericpinto.votingsessionservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_VOTING_SESSION = "votingsession";
    public static final String EXCHANGE_VOTING_SESSION = "exchangevotingsession";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_VOTING_SESSION, true);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE_VOTING_SESSION);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("routing_key");
    }

}
