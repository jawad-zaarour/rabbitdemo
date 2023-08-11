package com.alpha.rabbitmqdemo.service;

import com.alpha.rabbitmqdemo.model.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class ConsumerService implements ChannelAwareMessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Consume the RabbitMQ message and process it.
     *
     * @param properties The message properties.
     * @param messageDTO The parsed message body as a {@link MessageDTO} object.
     * @param channel    The RabbitMQ channel.
     */
    private void consume(MessageProperties properties, MessageDTO messageDTO, Channel channel) {
        log.info("Message {} Consumed from {} | Channel {}", messageDTO.getId(), properties.getConsumerQueue(), channel.getConnection());
        // Process the consumed message as needed
    }

    /**
     * This consumer listens to queued events in RabbitMQ and processes them.
     */
    @Override
    public void onMessage(Message message, Channel channel) throws InterruptedException {
        if (message == null) {
            log.warn("Received an empty message.");
            return;
        }

        MessageDTO messageDTO;
        try {
            messageDTO = objectMapper.readValue(new String(message.getBody()), MessageDTO.class);
        } catch (IOException ex) {
            log.error("Failed to parse message body due to: ", ex);
            return;
        }

        // Simulate message processing by introducing an artificial delay.
        // In a real application, perform the actual processing based on the messageDTO data.
        Thread.sleep(100);
        MessageProperties properties = message.getMessageProperties();
        consume(properties, messageDTO, channel);
    }
}
