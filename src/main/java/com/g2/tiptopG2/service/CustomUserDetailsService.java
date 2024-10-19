package com.g2.tiptopG2.service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.g2.tiptopG2.models.UserEntity;
import org.springframework.stereotype.Service;
import com.g2.tiptopG2.dto.UserResponseDto;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImp userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponseDto byLogin = userService.findByEmail(username);
        //ClientEntity byLogin=modelmapper.map(clientResponseDto, ClientEntity.class);
        

        if (byLogin == null) {
            return null;
        }
        return User.builder()
                .username(byLogin.getNom())
                //.password(byLogin.getPassword())
                //.roles(byLogin.getRole().name())
                .build();
    }
}
