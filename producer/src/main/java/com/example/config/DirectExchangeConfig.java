package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    @Bean
    public Queue queueDirectExchangeFirst() {
        return new Queue("first_direct", false);
    }

    @Bean
    public Queue queueDirectExchangeSecond() {
        return new Queue("second_direct", false);
    }

    @Bean("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange", false, false);
    }

    @Bean
    public Binding directBindingFirst() {
        return BindingBuilder.bind(queueDirectExchangeFirst()).to(directExchange()).with("first");
    }

    @Bean
    public Binding directBindingSecond() {
        return BindingBuilder.bind(queueDirectExchangeSecond()).to(directExchange()).with("second");
    }
}
