package com.g2.tiptopG2.contoller;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.g2.tiptopG2.models.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.management.modelmbean.ModelMBean;

import org.springframework.security.core.context.SecurityContextHolder;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IUserService;

@Controller
public class ClientController {
    private final IUserService userService;
    public ClientController(IUserService userService) {
        this.userService=userService;
    }


    @GetMapping("/client/parrametre")
    public String showSettingsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("user"));
        if (!hasUserRole) {
            return "403";
        }
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDto userDto = null;
            
            if (principal instanceof User) {
                String userEmail = ((User) principal).getUsername();
                userDto = userService.findByEmail(userEmail);
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                String userEmail = oauthUser.getAttribute("email");
                userDto = userService.findByEmail(userEmail);
            }
    
            model.addAttribute("user", userDto);
            return "client/parrametre";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            return "error";
        }
    }


    
    @GetMapping("/client/participation")
    public String histoGains() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("user"));
        if (!hasUserRole) {
            return "403";
        }
        return "/client/participation";
    }
    

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, Model model) {
        UserDto user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("errorMessage", "L'email n'existe pas.");
            model.addAttribute("email", email); // Conserve l'email saisi
            return "reset-password"; // Retourne à la page de saisie
        } else {
            // Appel de la méthode qui gère la mise à jour du mot de passe
            userService.updateMdp(user);
            model.addAttribute("successMessage", "Un e-mail de réinitialisation a été envoyé à " + email);
            return "mp-oublie"; // Redirige vers la page de succès
        }
    }

    
}
