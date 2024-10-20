package com.g2.tiptopG2.config;
import com.g2.tiptopG2.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.requestMatchers("/", "/resources/**", "/register", "/login","index","/css/**","/js/**","/image/**","/templates/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin()
                        .loginPage("/login")
                        .usernameParameter("username")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                        .and()
                .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true") // Rediriger après une déconnexion réussie
                        .invalidateHttpSession(true) // Invalider la session HTTP
                        .clearAuthentication(true)  // Effacer les informations d'authentification
                        .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  

  @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
