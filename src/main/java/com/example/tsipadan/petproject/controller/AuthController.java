package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.AuthRequestDTO;
import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.model.Role;
import com.example.tsipadan.petproject.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/security/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public Response authUser(@RequestBody AuthRequestDTO request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (user != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("session_state", UUID.randomUUID().toString());
            claims.put("currentDate", LocalDateTime.now().toString());
            return Response.builder()
                    .localDateTime(LocalDateTime.now())
                    .status(true)
                    .httpStatus(HttpStatus.OK)
                    .message(jwtUtils.generateToken(user, claims))
                    .authorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .build();
        }
        return Response.builder()
                .localDateTime(LocalDateTime.now())
                .status(false)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message("Something went wrong, look at logs")
                .build();
    }


}
