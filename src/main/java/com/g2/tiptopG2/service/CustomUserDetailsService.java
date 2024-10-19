package com.g2.tiptopG2.service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.g2.tiptopG2.models.UserEntity;
import org.springframework.stereotype.Service;
import com.g2.tiptopG2.dto.UserDto;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImp userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto byLogin = userService.findByEmail(username);
        //ClientEntity byLogin=modelmapper.map(clientResponseDto, ClientEntity.class);
        

        if (byLogin == null) {
            return null;
        }
        return User.builder()
                .username(byLogin.getNom())
                .password(byLogin.getMotDePasse())
                //.roles(new String[]{byLogin.getRoleId().toString()}) // Convertir en String[]
                .build();
    }
}
