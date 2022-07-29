package com.example.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProducerController {

    private final RabbitTemplate rabbitTemplate;

    public ProducerController(@Qualifier("basicDirectExchange") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/sendMessageDirect")
    @ResponseBody
    public ResponseEntity<String> sendMessageBasic(@RequestParam String input) {
        rabbitTemplate.convertAndSend("direct", input);
        System.out.println("Message sent: " + input);
        return ResponseEntity.ok(input + " successfully sent");
    }


}