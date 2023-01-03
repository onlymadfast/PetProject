package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.model.enumeration.Role;
import com.example.tsipadan.petproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);
        if (userOptional.isPresent()) {
            List<SimpleGrantedAuthority> authorities = Collections
                    .singletonList(new SimpleGrantedAuthority(Role.USER.name()));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userOptional.get().getUsername())
                    .password(userOptional.get().getPassword())
                    .roles(String.valueOf(authorities))
                    .build();
        } else
            throw new UsernameNotFoundException("Unknown user: " + username);
    }
}
