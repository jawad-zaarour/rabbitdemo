package com.jz.rabbitmqdemo.exception;

import org.springframework.amqp.rabbit.listener.exception.FatalListenerExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;

@ControllerAdvice
class GlobalHandlerException {

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionDetails> handleIllegalArgumentException(Exception ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QueueNotFoundException.class)
    public final ResponseEntity<ExceptionDetails> handleQueueNotFoundException(Exception ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QueuingException.class)
    public final ResponseEntity<ExceptionDetails> handleQueuingException(Exception ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConnectException.class)
    public final ResponseEntity<ExceptionDetails> handleConnectException(Exception ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FatalListenerExecutionException.class)
    public final ResponseEntity<ExceptionDetails> handleFatalListenerExecutionException(FatalListenerExecutionException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ExceptionDetails> handleIOException(IOException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}