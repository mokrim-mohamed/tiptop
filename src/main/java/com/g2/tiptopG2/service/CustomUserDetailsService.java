package com.g2.tiptopG2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dto.UserDto;

/**
 * Service personnalisé pour charger les détails de l'utilisateur.
 * Implémente l'interface UserDetailsService de Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImp userService;

    /**
     * Charge les détails de l'utilisateur par son nom d'utilisateur (email).
     * @param username Le nom d'utilisateur (email).
     * @return Les détails de l'utilisateur sous forme d'objet UserDetails.
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto byLogin = userService.findByEmail(username);

        if (byLogin == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(byLogin.getNom())
                .password(byLogin.getMotDePasse())
                //.roles(new String[]{byLogin.getRoleId().toString()}) // Convertir en String[]
                .build();
    }
}