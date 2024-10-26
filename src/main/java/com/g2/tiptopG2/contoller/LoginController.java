package com.g2.tiptopG2.contoller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.g2.tiptopG2.models.UserEntity;

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

    // Ajoutez d'autres méthodes pour d'autres pages protégées
}
