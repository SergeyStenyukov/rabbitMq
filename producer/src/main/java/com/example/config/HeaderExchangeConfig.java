package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderExchangeConfig {

    @Bean
    Queue firstHeaderQueue() {
        return new Queue("first_header", false);
    }

    @Bean
    Queue secondHeaderQueue() {
        return new Queue("second_header", false);
    }

    @Bean
    HeadersExchange headerExchange() {
        return new HeadersExchange("header_exchange");
    }
    @Bean
    Binding firstHeaderBinding() {
        return BindingBuilder.bind(firstHeaderQueue()).to(headerExchange())
                .where("header").matches("first");
    }

    @Bean
    Binding secondHeaderBinding() {
        return BindingBuilder.bind(secondHeaderQueue()).to(headerExchange())
                .where("header").matches("second");
    }
}
