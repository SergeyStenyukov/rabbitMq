package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutExchangeConfig {

    @Bean
    public Queue queueFanoutExchangeFirst() {
        return new Queue("first_fanout", false);
    }

    @Bean
    public Queue queueFanoutExchangeSecond() {
        return new Queue("second_fanout", false);
    }

    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange", false, false);
    }

    @Bean
    public Binding fanoutBindingFirst() {
        return BindingBuilder.bind(queueFanoutExchangeFirst()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBindingSecond() {
        return BindingBuilder.bind(queueFanoutExchangeSecond()).to(fanoutExchange());
    }
}
