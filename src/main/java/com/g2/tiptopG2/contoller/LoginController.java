package com.g2.tiptopG2.contoller;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login"; // Retourner la vue de login
    }
 

     @GetMapping("/index")
    public String indexPage() {
        return "index"; // Retourner la vue de la page admin
    }
 /* */   
 @GetMapping("/")
    public String index(Model model, Principal principal) {
        // Vérifiez si l'utilisateur est connecté
        boolean isAuthenticated = principal != null; // Vérifiez si l'utilisateur est connecté
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "index"; // Renvoie le nom du template index
    }
    // Ajoutez d'autres méthodes pour d'autres pages protégées
}
