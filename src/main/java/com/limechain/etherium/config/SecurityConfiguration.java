//package com.limechain.etherium.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//class SecurityConfiguration {
//
////    @Bean
////    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.csrf(AbstractHttpConfigurer::disable)
////                .cors(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL)
////                        .permitAll()
////                        .anyRequest()
////                        .authenticated())
////                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
////                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
////                .authenticationProvider(authenticationProvider())
////                .addFilterBefore(
////                        authenticationJwtTokenFilter(),
////                        UsernamePasswordAuthenticationFilter.class
////                );
////
////        return http.build();
////    }
//}
