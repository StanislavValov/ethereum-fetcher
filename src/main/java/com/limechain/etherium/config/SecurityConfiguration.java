package com.limechain.etherium.config;

import com.limechain.etherium.domain.service.CustomAuthenticationProvider;
import com.limechain.etherium.http.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter filter) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry.requestMatchers("/lime/authenticate").permitAll().anyRequest().authenticated()).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Add your custom JWT filter

            return http.build();
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomAuthenticationProvider provider) {
        return new ProviderManager(List.of(provider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}