package com.g2.tiptopG2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
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
