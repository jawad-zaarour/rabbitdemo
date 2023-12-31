package com.jz.rabbitmqdemo.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final RabbitMqProperties rabbitMqProperties = new RabbitMqProperties();

    @Data
    public static class RabbitMqProperties {
        private String nodes;
        private String connectionName;
        private String virtualHost;
        private String userName;
        private String password;
        private String exchangeName;
    }

}