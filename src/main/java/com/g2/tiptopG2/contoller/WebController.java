package com.g2.tiptopG2.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IUserService;

/**
 * Contrôleur web pour gérer les requêtes liées à l'enregistrement des utilisateurs.
 */
@Controller
public class WebController {

    @Autowired
    private IUserService userService;

    /**
     * Affiche le formulaire d'enregistrement.
     * @param model Le modèle pour lier les données du formulaire.
     * @return Le nom de la page HTML à afficher.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // On envoie un objet UserDto vide pour lier les données du formulaire
        model.addAttribute("user", new UserDto());
        return "register";  // Nom de la page HTML
    }

    /**
     * Gère la soumission du formulaire d'enregistrement.
     * @param userDto Les données de l'utilisateur soumises via le formulaire.
     * @return Une redirection vers la page de succès après enregistrement.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto) {
        // Sauvegarder l'utilisateur en base de données
        userService.save(userDto);
        // Redirection après enregistrement
        return "redirect:/register?success";
    }
}