package com.example.user_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // ChatGPT-Made. Hay que revisar y cambiar después.
        return http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF (para pruebas, en producción mejor configurarlo bien)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/add").authenticated() // Protege el endpoint
                        .anyRequest().permitAll() // Permite otros endpoints sin autenticación
                )
                .httpBasic(withDefaults()) // Usa autenticación básica (usuario/contraseña)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
                .build();
    }

}
