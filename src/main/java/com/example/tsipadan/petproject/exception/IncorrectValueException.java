package com.example.tsipadan.petproject.exception;

public class IncorrectValueException extends RuntimeException {

    public IncorrectValueException(String message) {
        super(message);
    }

    public IncorrectValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
