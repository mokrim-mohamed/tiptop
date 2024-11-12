package com.g2.tiptopG2.contoller;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.http.HttpStatus;

@Controller
public class EmployeeController {
    private final IUserService userService;
    public EmployeeController(IUserService userService) {
        this.userService=userService;
    }
    
    @GetMapping("employee/historique-gains")
    public String histoGain() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("employee"));
        if (!hasUserRole) {
            return "403";
        }
        return "employee/historique-gains";
    }

    @GetMapping("employee/parrametre")
    public String showSettingsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("employee"));
        if (!hasUserRole) {
            return "403";
        }
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = ((User) principal).getUsername();  
            UserDto userDto = userService.findByEmail(userEmail);
            model.addAttribute("user", userDto);
            return "employee/parrametre";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            return "error";
        }
    
    }

    @PostMapping("/employee/updateProfile")
    @ResponseBody
    public ResponseEntity<String> updateProfile(@RequestBody UserDto updatedUserDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = null;

        if (principal instanceof User) {
            userEmail = ((User) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            userEmail = ((OAuth2User) principal).getAttribute("email");
        }

        if (userEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié.");
        }

        UserDto existingUser = userService.findByEmail(userEmail);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable.");
        }

        existingUser.setNom(updatedUserDto.getNom());
        existingUser.setEmail(updatedUserDto.getEmail());
        existingUser.setTelephone(updatedUserDto.getTelephone());
        try {
            userService.updateUserProfile(existingUser);
            return ResponseEntity.ok("Profil mis à jour avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour du profil.");
        }
    }

}
