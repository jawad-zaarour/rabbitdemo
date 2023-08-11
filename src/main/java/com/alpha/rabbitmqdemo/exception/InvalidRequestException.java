package com.alpha.rabbitmqdemo.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Invalid request parameters");
    }
}