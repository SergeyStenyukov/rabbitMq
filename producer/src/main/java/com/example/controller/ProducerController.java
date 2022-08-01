package com.example.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        for (int i = 0; i < 100; i++){
            defaultTemplate.convertAndSend("default_direct", input + i);
            Thread.sleep(100);
            System.out.println("Message sent: " + input);
        }
        return ResponseEntity.ok(input + " successfully sent");
    }

    @PostMapping("/sendDirectExchange")
    public ResponseEntity<String> sendMessageDirectExchange(@RequestParam String input) throws InterruptedException {
        String rountingKey = null;
        for (int i = 0; i < 100; i++){
            if (i % 2 == 0)
                rountingKey = "first";
            else
                rountingKey = "second";

            amqpTemplate.convertAndSend("direct_exchange", rountingKey, input + i);
            Thread.sleep(100);
            System.out.println("Message sent: " + input);
        }
        return ResponseEntity.ok(input + " successfully sent");
    }


}