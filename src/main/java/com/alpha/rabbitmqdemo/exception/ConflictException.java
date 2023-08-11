package com.alpha.rabbitmqdemo.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String listener) {
        super(listener + " is already exists");
    }
}