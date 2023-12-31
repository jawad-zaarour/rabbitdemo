package com.jz.rabbitmqdemo.controller;

import com.jz.rabbitmqdemo.service.ListenerQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Properties;

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

    @PostMapping("/{queueName}")
    public ResponseEntity<String> addQueue(@PathVariable("queueName")  String queueName){
        listenerQueueService.addQueue(queueName);
        return new ResponseEntity<>("Queue added successfully: " + queueName, HttpStatus.CREATED);
    }

    @DeleteMapping("/{queueName}")
    public ResponseEntity<String> removeQueue(@PathVariable("queueName") String queueName) {
        listenerQueueService.removeQueue(queueName);
        return ResponseEntity.ok("Queue removed successfully: " + queueName);
    }

    @GetMapping
    public ResponseEntity<List<Properties>> getQueues() {
        List<Properties> queues = listenerQueueService.getQueues();
        return ResponseEntity.ok(queues);
    }

    @PostMapping(value = "/stop")
    public ResponseEntity<Void> stopContainer() {
        listenerQueueService.stopContainer();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/start")
    public ResponseEntity<Void> startContainer() {
        listenerQueueService.startContainer();
        return ResponseEntity.ok().build();
    }
}
