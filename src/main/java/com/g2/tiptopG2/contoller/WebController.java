package com.g2.tiptopG2.contoller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.g2.tiptopG2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IUserService;
@Controller
public class WebController {
 

   @Autowired
    private IUserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // On envoie un objet UserDto vide pour lier les données du formulaire
        model.addAttribute("user", new UserDto());
        return "register";  // Nom de la page HTML
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto) {
        // Sauvegarder l'utilisateur en base de données
        userService.save(userDto);
        // Redirection après enregistrement
        return "redirect:/register?success";
    }

    @GetMapping("/rse")
    public String getRsePage() {
        return "rse";
    }

    @GetMapping("/faq")
    public String getFaqPage() {
        return "faq";
    }

    @GetMapping("/cgu")
    public String getCguPage() {
        return "cgu";
    }

    @GetMapping("/mentionslegals")
    public String getMlPage() {
        return "mentionsLegals";
    }

  
}
