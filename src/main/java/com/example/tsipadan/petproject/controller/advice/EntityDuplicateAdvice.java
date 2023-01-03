package com.example.tsipadan.petproject.controller.advice;

import com.example.tsipadan.petproject.exception.EntityDuplicateException;
import com.example.tsipadan.petproject.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntityDuplicateAdvice {

    @ResponseBody
    @ExceptionHandler(EntityDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String entityNotFoundHandler(EntityDuplicateException ex) {
        return ex.getMessage();
    }

}
