package com.g2.tiptopG2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/").permitAll() 
                .requestMatchers("/css/**").permitAll() 
                .requestMatchers("/js/**").permitAll() 
                
                // Permettre l'accès à la page d'index et aux fichiers statiques
                .requestMatchers("/login").permitAll() // Autoriser tout le monde à accéder à la page de login
                .requestMatchers("/admin/**").authenticated() // Protéger les pages sous /admin
                .anyRequest().authenticated() // Toutes les autres pages nécessitent une authentification
            )
            .formLogin(form -> form
                .loginPage("/login") // Spécifier la page de connexion personnalisée
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager auth(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
