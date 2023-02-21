package com.example.tsipadan.petproject.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Response {

    private LocalDateTime localDateTime;
    private boolean status;
    private HttpStatus httpStatus;
    private String message;
    private Map<String, Object> records;

    @Builder
    public Response(LocalDateTime localDateTime, boolean status,
                    String message,
                    @Nullable Map<String, Object> records) {
        this.localDateTime = localDateTime;
        this.status = status;
        this.message = message;
        if (records != null) {
            this.records = records;
        }
    }

}
