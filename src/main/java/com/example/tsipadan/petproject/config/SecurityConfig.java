package com.example.tsipadan.petproject.config;

import com.example.tsipadan.petproject.model.enumeration.Role;
import com.example.tsipadan.petproject.service.implementation.CustomUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailServiceImpl userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Отключает CSRF Protection, поскольку она не нужна для API
                .csrf().disable()
                .sessionManagement().disable();
                //Декларирует, что все запросы к любой конечной точке должны быть авторизованы, иначе они должны быть отклонены
//                .authorizeRequests().anyRequest().authenticated()
                //сообщает Spring, чтобы он ожидал базовую HTTP аутентификацию
//                .and().httpBasic()
                //сообщает Spring, что не следует хранить информацию о сеансе для пользователей, поскольку это не нужно для API
//                .and().sessionManagement().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
