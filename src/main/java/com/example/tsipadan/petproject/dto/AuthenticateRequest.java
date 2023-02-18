package com.example.tsipadan.petproject.dto;

import lombok.Data;

@Data
public class AuthenticateRequest {

    private String username;
    private String password;
}
