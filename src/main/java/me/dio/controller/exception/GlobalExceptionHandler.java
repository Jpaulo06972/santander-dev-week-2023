package me.dio.controller.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    // CORREÇÃO 1: Mudei de IllegalAccessException para IllegalArgumentException
    // (Geralmente é essa que usamos para validação de regras de negócio)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException businessException) {
        return new ResponseEntity<>(businessException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // CORREÇÃO 2: A anotação agora corresponde ao tipo da exceção (NoSuchElementException)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handle(NoSuchElementException notFoundException) {
        return new ResponseEntity<>("Resource ID not found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleUnexpectedException(Throwable unexpectedException) {
        var message = "Unexpected server error, see the logs.";
        logger.error(message, unexpectedException);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}