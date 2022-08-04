package com.example.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Value("${rabbit.host}")
    private String host;

    @Value("${rabbit.port}")
    private Integer port;

    @Value("${rabbit.username}")
    private String user;

    @Value("${rabbit.password}")
    private String pass;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost(host);
        cf.setPort(port);
        cf.setUsername(user);
        cf.setPassword(pass);
        return cf;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue defaultQueue() {
        return new Queue("default_direct", false);
    }

    @Bean
    public Queue directReplyTo() {
        return new Queue("direct_reply_to", false);
    }

    @Bean
    public void initQueues() {
        amqpAdmin().declareQueue();
    }
}
