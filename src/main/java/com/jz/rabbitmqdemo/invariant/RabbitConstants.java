package com.jz.rabbitmqdemo.invariant;

/**
 * This class contains constants used for RabbitMQ configuration and bean names.
 *
 * @Author Jawad Zaarour
 */
public class RabbitConstants {
    // RabbitMQ Bean Names
    public static final String RABBIT_SEND_TEMPLATE = "rabbitSendTemplate";
    public static final String RABBIT_CONNECTION_FACTORY = "rabbitConnectionFactory";
    public static final String RABBIT_ADMIN = "rabbitAdmin";
    public static final String SIMPLE_MESSAGE_LISTENER_CONTAINER = "simpleMessageListenerContainer";

    // Default RabbitMQ Configuration
    public static final String DEFAULT_LOCALHOST = "localhost:5672";
    public static final String DEFAULT_VH = "/";
    public static final String DEFAULT_USERNAME_PASSWORD = "guest";
    public static final String DEFAULT_CONNECTION_NAME = "Connection";
}

