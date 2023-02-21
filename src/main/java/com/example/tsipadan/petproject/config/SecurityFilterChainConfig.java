package com.example.tsipadan.petproject.config;

import com.example.tsipadan.petproject.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                     JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and().csrf().disable()
                .authorizeRequests()

                //addressController
                .antMatchers( "/user/address/**").hasAnyRole("ADMIN","USER")
                //authController
                .antMatchers(HttpMethod.POST, "/security/auth/authenticate").permitAll()
                //itemController
                .antMatchers("/items/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/items/edit/**").hasRole("ADMIN")
                //orderController
                .antMatchers(HttpMethod.GET, "/order/orders/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST, "/order/make/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/order/edit/**").hasRole("ADMIN")
                //userController
                .antMatchers(HttpMethod.GET, "/user/access/list").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/access/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/user/access/create").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/access/edit/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.PUT, "/user/access/edit/pass/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/user/access/edit/**").hasRole("ADMIN")
                //roleController
                .antMatchers("/role/access/**").hasRole("ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> response
                        .sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Allow-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
