package com.jz.rabbitmqdemo.service;

import com.jz.rabbitmqdemo.invariant.RabbitConstants;
import com.jz.rabbitmqdemo.model.MessageDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@Log4j2
public class MessageService {

    private final RabbitTemplate rabbitTemplate;

    public MessageService(@Qualifier(RabbitConstants.RABBIT_SEND_TEMPLATE) RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Generates n messages to a specific queue.
     *
     * @param numMessages The number of messages to generate.
     * @param routingKey  The routing key for the queue.
     */
    public void generateMessages(int numMessages, String routingKey) {
        IntStream.range(0, numMessages).forEach(i -> generateMessage(routingKey, i+1));
    }

    /**
     * Generates a single message to a specific queue.
     *
     * @param messageDTO  The message data to send.
     * @param routingKey  The routing key for the queue.
     */
    public void generateMessage(MessageDTO messageDTO, String routingKey) {
        try {
            sendMessage(messageDTO, routingKey);
        } catch (AmqpException ex) {
            log.error("Failed to send message due to: {}", ex.getMessage());
        }
    }

    private void generateMessage(String routingKey, int i) {
        MessageDTO messageDTO = MessageDTO.builder()
                .id("M" + i)
                .build();
        sendMessage(messageDTO, routingKey);
    }

    private void sendMessage(MessageDTO messageDTO, String routingKey) {
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.convertAndSend(messageDTO);
        log.info("Message {} event exchanged successfully to {}", messageDTO.getId(), rabbitTemplate.getRoutingKey());
    }
}
