package com.g2.tiptopG2.contoller;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.eclipse.angus.mail.iap.Response;
import org.springframework.http.HttpStatus;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IUserService;

@Controller
public class ClientController {
    private final IUserService userService;
    public ClientController(IUserService userService) {
        this.userService=userService;
    }


    @GetMapping("client/parrametre")
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


    
    @GetMapping("client/participation")
    public String histoGains() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("user"));
        if (!hasUserRole) {
            return "403";
        }
        return "client/participation";
    }
    

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, Model model) {
        UserDto user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("errorMessage", "L'email n'existe pas.");
            model.addAttribute("email", email); // Conserve l'email saisi
            return "mp-oublie"; // Retourne à la page de saisie
        } else {
            // Appel de la méthode qui gère la mise à jour du mot de passe
            userService.updateMdp(user);
            model.addAttribute("successMessage", "Un e-mail de réinitialisation a été envoyé à " + email);
            return "mp-oublie"; // Redirige vers la page de succès
        }
    }

    @PostMapping("/client/updateProfile")
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

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updatePassword(@RequestParam String currentPassword,
                                                            @RequestParam String newPassword,
                                                            @RequestParam String confirmPassword) {
        Map<String, String> response = new HashMap<>();

        // Vérifier si les mots de passe sont identiques
        if (!newPassword.equals(confirmPassword)) {
            response.put("status", "error");
            response.put("message", "Les nouveaux mots de passe ne sont pas identiques.");
            return ResponseEntity.badRequest().body(response);
        }

        // Récupérer l'utilisateur authentifié
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof OAuth2User) {
            response.put("status", "error");
            response.put("message", "Vous ne pouvez pas modifier votre mot de passe.");
            return ResponseEntity.badRequest().body(response);
        }

        // Récupérer l'email de l'utilisateur connecté
        String userEmail = ((User) principal).getUsername();
        UserDto userDto = userService.findByEmail(userEmail);

        // Vérifier l'ancien mot de passe
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(currentPassword, userDto.getMotDePasse())) {
            response.put("status", "error");
            response.put("message", "L'ancien mot de passe est incorrect.");
            return ResponseEntity.badRequest().body(response);
        }

        // Mettre à jour le mot de passe
        userDto.setMotDePasse(passwordEncoder.encode(newPassword));  // Hacher le nouveau mot de passe
        userService.updateUserPassword(userDto);

        response.put("status", "success");
        response.put("message", "Mot de passe mis à jour avec succès.");

        return ResponseEntity.ok(response);
    }

 }
