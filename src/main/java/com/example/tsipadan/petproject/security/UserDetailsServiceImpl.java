package com.example.tsipadan.petproject.security;

import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User < " + username + " > doesn't exist"));
        return mapUserToUserPrincipal(user);
    }

    private UserPrincipal mapUserToUserPrincipal(User user) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername(user.getUsername());
        userPrincipal.setPassword(user.getPassword());
        userPrincipal.setAuthorities(
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                        .collect(Collectors.toList())
        );
        userPrincipal.setAccountNonExpired(true);
        userPrincipal.setAccountNonLocked(true);
        userPrincipal.setCredentialsNonExpired(true);
        userPrincipal.setEnabled(true);
        return userPrincipal;
    }
}
