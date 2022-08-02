package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicExchangeConfig {

    @Bean
    Queue firstTopicQueue() {
        return new Queue("first_topic", false);
    }

    @Bean
    Queue secondTopicQueue() {
        return new Queue("second_topic", false);
    }

    @Bean
    Queue generalTopicQueue() {
        return new Queue("general_topic", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic_exchange");
    }

    @Bean
    Binding firstBinding() {
        return BindingBuilder.bind(firstTopicQueue()).to(topicExchange()).with("queue.first_topic");
    }

    @Bean
    Binding secondBinding() {
        return BindingBuilder.bind(secondTopicQueue()).to(topicExchange()).with("queue.second_topic");
    }

    @Bean
    Binding generalBinding() {
        return BindingBuilder.bind(generalTopicQueue()).to(topicExchange()).with("queue.*");
    }
}
