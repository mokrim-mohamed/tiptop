package com.g2.tiptopG2.config;
import com.g2.tiptopG2.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
        http.csrf().disable()
                .authorizeHttpRequests(request -> request.requestMatchers("/","reset-password","reset-password-success", "/resources/**", "/register", "/login","index","/css/**","/js/**","/image/**","/templates/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin()
                        .loginPage("/login")
                        .usernameParameter("username")
                        .failureUrl("/login?error=true")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                        .and()
                    .oauth2Login()  // Activer OAuth2 pour Google login
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
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


    @Bean
public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    return (request, response, authentication) -> {
        // Récupérer les rôles de l'utilisateur connecté
        var authorities = authentication.getAuthorities();
        String redirectUrl = "/index"; // URL par défaut

        // Rediriger en fonction du rôle
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
            redirectUrl = "/admin/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("employee"))) {
            redirectUrl = "/employee/historique-gains";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("user"))) {
            redirectUrl = "/client/participation";
        }

        response.sendRedirect(redirectUrl); // Rediriger vers l'URL appropriée
    };
}
}