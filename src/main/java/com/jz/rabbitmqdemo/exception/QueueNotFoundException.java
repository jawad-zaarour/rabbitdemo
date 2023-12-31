package com.jz.rabbitmqdemo.exception;

public class QueueNotFoundException extends RuntimeException {
    public QueueNotFoundException(String message) {
        super(message);

    }
}
