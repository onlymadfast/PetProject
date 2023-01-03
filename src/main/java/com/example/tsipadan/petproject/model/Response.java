package com.example.tsipadan.petproject.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Response {

    protected LocalDateTime localDateTime;
    protected HttpStatus status;
    protected String message;
    protected String devMessage;
    protected Map<String, Object> data;

}
