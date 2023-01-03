package com.example.tsipadan.petproject.exception;

public class EntityDuplicateException extends RuntimeException {

    public EntityDuplicateException(String message) {
        super(message);
    }

    public EntityDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
