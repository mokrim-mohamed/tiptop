package com.g2.tiptopG2.contoller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.g2.tiptopG2.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import java.security.Principal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.g2.tiptopG2.dto.UserDto;
import java.util.Collection;
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
    @GetMapping("/contacteznous")
    public String getContactPage(Model model, Principal principal) {
        boolean isAuthenticated = principal != null;
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

        return "contacteznous";
    }
    
}
