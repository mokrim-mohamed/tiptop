package com.g2.tiptopG2.controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login"; // Retourner la vue de login
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // Retourner la vue de la page admin
    }
     @GetMapping("/index")
    public String indexPage() {
        return "index"; // Retourner la vue de la page admin
    }

    // Ajoutez d'autres méthodes pour d'autres pages protégées
}
