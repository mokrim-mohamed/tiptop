package com.g2.tiptopG2.contoller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.g2.tiptopG2.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.g2.tiptopG2.models.UserEntity;
import org.springframework.security.core.userdetails.User;

import javax.management.modelmbean.ModelMBean;

import org.springframework.security.core.context.SecurityContextHolder;
import com.g2.tiptopG2.dto.UserDto;
@Controller
public class ClientController {
    private final IUserService userService;
    public ClientController(IUserService userService) {
        this.userService=userService;
    }


    @GetMapping("/client/parrametre")
public String showSettingsPage(Model model) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = ((User) principal).getUsername();  
            UserDto userDto = userService.findByEmail(userEmail);
            model.addAttribute("user", userDto);
            return "client/parrametre";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            return "error";
        }
    
} 
    // hadi lizdty nta 
 //   @GetMapping("/client/historique-gains")
  //  public String histoGain() {
  //      return "/client/historique-gains";
  //  }

    @GetMapping("/client/participation")
    public String histoGains() {
        return "/client/participation";
    }
    @GetMapping("/reset-password")
    public String showResetPasswordForm() {
        return "reset-password"; // Nom de la vue pour le formulaire
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
            return "reset-password-success"; // Redirige vers la page de succès
        }
    }
    
}
