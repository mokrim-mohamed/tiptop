package com.g2.tiptopG2.service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.g2.tiptopG2.models.UserEntity;
import org.springframework.stereotype.Service;
import com.g2.tiptopG2.dto.UserDto;
import java.util.Collections;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImp userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto byLogin = userService.findByEmail(username);
        //ClientEntity byLogin=modelmapper.map(clientResponseDto, ClientEntity.class);
        

        if (byLogin == null) {
            throw new UsernameNotFoundException("user mot found");
        }
        
        return new User(byLogin.getEmail(),byLogin.getMotDePasse(),Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        
    }
}