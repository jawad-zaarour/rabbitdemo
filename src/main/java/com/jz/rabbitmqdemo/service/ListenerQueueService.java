package com.jz.rabbitmqdemo.service;

import com.jz.rabbitmqdemo.exception.QueuingException;
import com.jz.rabbitmqdemo.exception.QueueNotFoundException;
import com.jz.rabbitmqdemo.invariant.RabbitConstants;
import com.jz.rabbitmqdemo.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ListenerQueueService {

    private final SimpleMessageListenerContainer listenerContainer;
    private final RabbitAdmin rabbitAdmin;

    public ListenerQueueService(@Qualifier(RabbitConstants.SIMPLE_MESSAGE_LISTENER_CONTAINER) SimpleMessageListenerContainer listenerContainer,
                                @Qualifier(RabbitConstants.RABBIT_ADMIN) RabbitAdmin rabbitAdmin) {
        this.listenerContainer = listenerContainer;
        this.rabbitAdmin = rabbitAdmin;
    }

    /**
     * Adds a queue to the listener container for message consumption.
     *
     * @param queueName The name of the queue to add.
     */
    public void addQueue(String queueName) {
        if (!queueExists(queueName)) {
            throw new QueueNotFoundException("Queue not found: " + queueName);
        }

        try {
            listenerContainer.addQueueNames(queueName);
        } catch (AmqpException e) {
            throw new QueuingException(Utils.getExceptionString(e));
        }
    }

    /**
     * Retrieves the names of all queues currently being listened to.
     *
     * @return An array of queue names.
     */
    public List<Properties> getQueues() {
        try {
            return Arrays.stream(listenerContainer.getQueueNames())
                    .sequential()
                    .map(rabbitAdmin::getQueueProperties)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new QueuingException(Utils.getException(e));
        }
    }

    /**
     * Removes a queue from the listener container.
     *
     * @param queueName The name of the queue to remove.
     */
    public void removeQueue(String queueName) {
        try {
            listenerContainer.removeQueueNames(queueName);
        } catch (Exception e) {
            throw new QueuingException(Utils.getException(e));
        }
    }

    /**
     * Stops the listener container.
     */
    public void stopContainer() {
        try {
        listenerContainer.stop();
        } catch (Exception e) {
            throw new QueuingException(Utils.getException(e));
        }
    }

    /**
     * Starts the listener container.
     */
    public void startContainer() {
        listenerContainer.start();
    }

    private boolean queueExists(String queueName) {
        try {
            return rabbitAdmin.getQueueProperties(queueName) != null;
        } catch (AmqpException e) {
            throw new QueuingException(Utils.getException(e));
        }

    }
}
