package com.jz.rabbitmqdemo.config;

import com.jz.rabbitmqdemo.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

import static com.jz.rabbitmqdemo.invariant.RabbitConstants.*;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    public static final int MAX_CONCURRENT_CONSUMERS = 5;
    public static final int PREFETCH_COUNT = 1;
    private final ApplicationProperties applicationProperties;

    private ApplicationProperties.RabbitMqProperties getRabbitMqProperties() {
        return applicationProperties.getRabbitMqProperties();
    }

    @Bean(RABBIT_CONNECTION_FACTORY)
    public ConnectionFactory connectionFactory() {
        String nodes = Objects.requireNonNullElse(getRabbitMqProperties().getNodes(), DEFAULT_LOCALHOST);
        String virtualHost = Objects.requireNonNullElse(getRabbitMqProperties().getVirtualHost(), DEFAULT_VH);
        String userName = Objects.requireNonNullElse(getRabbitMqProperties().getUserName(), DEFAULT_USERNAME_PASSWORD);
        String password = Objects.requireNonNullElse(getRabbitMqProperties().getPassword(), DEFAULT_USERNAME_PASSWORD);
        String connectionName = Objects.requireNonNullElse(getRabbitMqProperties().getConnectionName(), DEFAULT_CONNECTION_NAME);

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(nodes);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setConnectionNameStrategy(c -> connectionName);
        return connectionFactory;
    }

    @Bean(RABBIT_SEND_TEMPLATE)
    public RabbitTemplate sendRabbitTemplate(@Qualifier(RABBIT_CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        String exchange = Objects.requireNonNull(getRabbitMqProperties().getExchangeName());

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean(SIMPLE_MESSAGE_LISTENER_CONTAINER)
    public SimpleMessageListenerContainer simpleMessageListenerContainer(@Qualifier(RABBIT_CONNECTION_FACTORY) ConnectionFactory connectionFactory) throws AmqpException {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setMaxConcurrentConsumers(MAX_CONCURRENT_CONSUMERS);
        container.setPrefetchCount(PREFETCH_COUNT);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(new ConsumerService());
        container.setMissingQueuesFatal(true);
        return container;
    }

    @Bean(RABBIT_ADMIN)
    public RabbitAdmin rabbitAdmin(@Qualifier(RABBIT_CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
