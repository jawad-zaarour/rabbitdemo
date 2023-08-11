package com.alpha.rabbitmqdemo.service;

import com.alpha.rabbitmqdemo.invariant.RabbitConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
     * @return A success or error message based on the operation's outcome.
     */
    public String addQueue(String queueName) {
        if (!queueExists(queueName)) {
            return "Queue not found: " + queueName;
        }

        try {
            listenerContainer.addQueueNames(queueName);
            return "Listener added successfully to queue: " + queueName;
        } catch (AmqpException e) {
            log.error("Failed to listen to {} due to {}: ", queueName, e.getMessage());
            return "Failed to add listener for queue: " + queueName;
        }
    }

    /**
     * Retrieves the names of all queues currently being listened to.
     *
     * @return An array of queue names.
     */
    public String[] getQueues() {
        return listenerContainer.getQueueNames();
    }

    /**
     * Removes a queue from the listener container.
     *
     * @param queueName The name of the queue to remove.
     */
    public void removeQueue(String queueName) {
        listenerContainer.removeQueueNames(queueName);
    }

    /**
     * Stops the listener container.
     */
    public void stopContainer() {
        listenerContainer.stop();
    }

    /**
     * Starts the listener container.
     */
    public void startContainer() {
        listenerContainer.start();
    }

    private boolean queueExists(String queueName) {
        return rabbitAdmin.getQueueProperties(queueName) != null;
    }
}
