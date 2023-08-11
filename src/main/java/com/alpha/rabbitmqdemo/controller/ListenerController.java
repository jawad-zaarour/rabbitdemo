package com.alpha.rabbitmqdemo.controller;

import com.alpha.rabbitmqdemo.service.ListenerQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents an API controller for managing RabbitMQ listeners and their associated queues.
 * It provides endpoints to add, remove, get queues, and control the listener container using a {@link ListenerQueueService}.
 *
 * <p>The base URL for all listener-related endpoints is "/api/listeners".
 *
 * @Author Jawad Zaarour
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/listeners")
public class ListenerController {

    private final ListenerQueueService listenerQueueService;

    @PostMapping
    public ResponseEntity<String> addQueue(@RequestParam("queueName")  String queueName){
        String response = listenerQueueService.addQueue(queueName);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> removeQueue(@RequestParam("queueName") String queueName) {
        listenerQueueService.removeQueue(queueName);
        return ResponseEntity.ok("Queue removed successfully: " + queueName);
    }

    @GetMapping
    public ResponseEntity<String[]> getQueues() {
        String[] queues = listenerQueueService.getQueues();
        return ResponseEntity.ok(queues);
    }

    @PostMapping(value = "/stop")
    public ResponseEntity<String> stopContainer() {
        listenerQueueService.stopContainer();
        return ResponseEntity.ok("Listener container stopped successfully.");

    }

    @PostMapping(value = "/start")
    public ResponseEntity<String> startContainer() {
        listenerQueueService.startContainer();
        return ResponseEntity.ok("Listener container started successfully.");
    }
}
