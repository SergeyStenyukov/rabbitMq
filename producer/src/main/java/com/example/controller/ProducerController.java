package com.example.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ProducerController {

    private final RabbitTemplate defaultTemplate;

    private AmqpTemplate amqpTemplate;

    public ProducerController(@Qualifier("defaultDirectExchange") RabbitTemplate defaultTemplate,
                              AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
        this.defaultTemplate = defaultTemplate;

    }

    @PostMapping("/sendMessageDefault")
    public ResponseEntity<String> sendMessageDefault(@RequestParam String input) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            defaultTemplate.convertAndSend("default_direct", input + i);
            Thread.sleep(100);
            System.out.println("Message sent: " + input + " " + i);
        }
        return ResponseEntity.ok(input + " successfully sent");
    }

    @PostMapping("/sendDirectExchange")
    public ResponseEntity<String> sendMessageDirectExchange(@RequestParam String input) throws InterruptedException {
        String rountingKey;
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0)
                rountingKey = "first";
            else
                rountingKey = "second";

            amqpTemplate.convertAndSend("direct_exchange", rountingKey, input + i);
            Thread.sleep(100);
            System.out.println("Message sent: " + input + " " + i);
        }
        return ResponseEntity.ok(input + " successfully sent");
    }

    @PostMapping("/sendFanoutExchange")
    public ResponseEntity<String> sendMessageFanoutExchange(@RequestParam String input) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("fanout_exchange", "", input + i);
            Thread.sleep(100);
            System.out.println("Message sent: " + input + " " + i);
        }
        return ResponseEntity.ok(input + " successfully sent");
    }

    @PostMapping("/sendTopicExchange")
    public ResponseEntity<String> sendMessageTopicExchange(@RequestParam String input) throws InterruptedException {
        String rountingKey;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0)
                rountingKey = "queue.first_topic";
            else
                rountingKey = "queue.second_topic";
            amqpTemplate.convertAndSend("topic_exchange", rountingKey, input + i);
            Thread.sleep(100);
            System.out.println("Message sent: " + input + " " + i);
        }
        return ResponseEntity.ok(input + " successfully sent");
    }

    @PostMapping("/sendHeaderExchange")
    public ResponseEntity<String> sendMessageHeaderExchange(@RequestParam String input) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            MessageProperties msgProperties = new MessageProperties();
            if (i % 2 == 0)
                msgProperties.setHeader("header", "first");
            else
                msgProperties.setHeader("header", "second");

            MessageConverter messageConverter = new SimpleMessageConverter();
            Message message = messageConverter.toMessage(input, msgProperties);

            amqpTemplate.convertAndSend("header_exchange", "", message);
            Thread.sleep(100);
            System.out.println("Message sent: " + input + " " + i);
        }
        return ResponseEntity.ok(input + " successfully sent");
    }
}