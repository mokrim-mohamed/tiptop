package com.g2.tiptopG2.contoller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.g2.tiptopG2.models.UserEntity;

@Controller
public class AdminController {
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "/admin/dashboard"; // Nom du fichier Thymeleaf
    }
    @GetMapping("/admin/user")
    public String adminUser() {
        return "/admin/user"; // Nom du fichier Thymeleaf
    }
    @GetMapping("/admin/parrametre")
public String showSettingsPage(Model model) {
    // Création d'un utilisateur fictif
    UserEntity fictiveUser = new UserEntity();
    fictiveUser.setNom("John Doe");
    fictiveUser.setEmail("john.doe@example.com");
    fictiveUser.setTelephone("123-456-7890");
    
    // Ajout de l'utilisateur au modèle
    model.addAttribute("user", fictiveUser);
    
    return "admin/parrametre";
}

}
