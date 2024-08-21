package com.cybersoft.uniclub.security;

import com.cybersoft.uniclub.authenprovider.CustomAuthenProvider;
import com.cybersoft.uniclub.filter.CustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http, CustomAuthenProvider customAuthenProvider) throws Exception {
        return http.getSharedObject((AuthenticationManagerBuilder.class))
                .authenticationProvider(customAuthenProvider)
                .build();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomFilter customFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->{
                    request.requestMatchers("/authen").permitAll();
                    request.requestMatchers(HttpMethod.POST,"/users/*").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN");
                    request.anyRequest().authenticated();
                })
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
