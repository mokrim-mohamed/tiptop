package com.g2.tiptopG2.contoller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collection;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login"; // Retourner la vue de login
    }
    @GetMapping("/mp-oublie")
    public String login() {

        return "mp-oublie"; // Retourner la vue de login
    }

    @GetMapping({"/", "/home", "/index"})
    public String index(Model model, Principal principal) {
        // Vérifiez si l'utilisateur est connecté
        boolean isAuthenticated = principal != null;
        System.out.println("Authenticated: " + isAuthenticated);

        // Initialiser les rôles
        boolean isAdmin = false;
        boolean isEmployee = false;
        boolean isUser = false;

        if (isAuthenticated) {
            // Obtenir l'authentification actuelle
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            // Vérifier les rôles
            isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
            isEmployee = authorities.stream().anyMatch(a -> a.getAuthority().equals("employee"));
            isUser = authorities.stream().anyMatch(a -> a.getAuthority().equals("user"));
        }

        // Ajouter les informations au modèle
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isEmployee", isEmployee);
        model.addAttribute("isUser", isUser);

        return "index"; // Renvoie le nom du template index
    }
    // Ajoutez d'autres méthodes pour d'autres pages protégées
}
