package com.alpha.rabbitmqdemo.controller;

import com.alpha.rabbitmqdemo.model.MessageDTO;
import com.alpha.rabbitmqdemo.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents an API controller for managing messages to be sent to a RabbitMQ broker.
 * It provides endpoints to generate and send messages to the broker using a {@link MessageService}.
 * The messages can be either single or multiple to specific or random queue based on the routing key.
 *
 * <p>The base URL for all message-related endpoints is "/api/messages".
 *
 * @Author Jawad Zaarour
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> generateSingleMessage(@RequestBody MessageDTO messageDTO, @RequestParam("routingKey") String routingKey) {
        messageService.generateMessage(messageDTO, routingKey);
        return ResponseEntity.ok(messageDTO);
    }

    @PostMapping("/{numberOfMessages}")
    public ResponseEntity<Void> generateMultipleMessages(@PathVariable int numberOfMessages, @RequestParam("routingKey") String routingKey) {
        messageService.generateMessages(numberOfMessages, routingKey);
        return ResponseEntity.ok().build();
    }
}
