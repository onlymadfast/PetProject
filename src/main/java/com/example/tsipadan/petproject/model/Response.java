package com.example.tsipadan.petproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private LocalDateTime localDateTime;
    private boolean status;
    private HttpStatus httpStatus;
    private String message;
    private Map<String, Object> records;
    private List<String> authorities;

    @Builder
    public Response(LocalDateTime localDateTime, boolean status, String message,
                    @Nullable Map<String, Object> records,
                    @Nullable HttpStatus httpStatus,
                    @Nullable List<String> authorities) {
        this.localDateTime = localDateTime;
        this.status = status;
        this.message = message;
        if (records != null) {
            this.records = records;
        }
        if (httpStatus != null) {
            this.httpStatus = httpStatus;
        }
        if (authorities != null) {
            this.authorities = authorities;
        }
    }

}
