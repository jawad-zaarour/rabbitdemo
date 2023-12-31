package com.jz.rabbitmqdemo.exception;

public class QueuingException extends RuntimeException {

    public QueuingException(String message) {
        super(message);
    }
}
