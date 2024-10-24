package com.g2.tiptopG2.contoller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.g2.tiptopG2.models.UserEntity;

@Controller
public class ClientController {


    @GetMapping("/client/parrametre")
public String showSettingsPage(Model model) {
    // Création d'un utilisateur fictif
    UserEntity fictiveUser = new UserEntity();
    fictiveUser.setNom("John Doe");
    fictiveUser.setEmail("john.doe@example.com");
    fictiveUser.setTelephone("123-456-7890");
    
    // Ajout de l'utilisateur au modèle
    model.addAttribute("user", fictiveUser);
    
    return "client/parrametre";
}
    // hadi lizdty nta 
    @GetMapping("/client/historique-gains")
    public String histoGain() {
        return "/client/historique-gains";
    }

    @GetMapping("/client/participation")
    public String histoGains() {
        return "/client/participation";
    }
}
