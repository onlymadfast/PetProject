package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.AuthRequestDTO;
import com.example.tsipadan.petproject.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/security/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authUser(@RequestBody AuthRequestDTO request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (user != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("session_state", UUID.randomUUID().toString());
            claims.put("currentDate", LocalDateTime.now().toString());
            return ResponseEntity.ok(jwtUtils.generateToken(user, claims));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
