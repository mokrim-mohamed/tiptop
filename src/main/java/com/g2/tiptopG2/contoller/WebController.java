package com.g2.tiptopG2.contoller;
import java.security.Principal;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("user", new UserDto());
        String siteKey = "68426f62-53ae-4d18-a086-7c405668406c";
        model.addAttribute("siteKey", siteKey);
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto, Model model) {
        try {
            userService.save(userDto);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login"; // Assurez-vous que "registre" correspond au nom de la vue de votre page d'inscription
        }
    }
    
    @GetMapping("/rse")
    public String getRsePage(Model model, Principal principal) {
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
       model.addAttribute("isUser", isUser);return "rse";
    }

    @GetMapping("/faq")
    public String getFaqPage(Model model, Principal principal) {
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
       model.addAttribute("isUser", isUser); return "faq";
    }

    @GetMapping("/cgu")
    public String getCguPage(Model model, Principal principal) {
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
       model.addAttribute("isUser", isUser);return "cgu";
    }

    @GetMapping("/mentionslegals")
    public String getMlPage(Model model, Principal principal) {
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
    
    @GetMapping("/politiquedeconfidentialite")
    public String getPDC(Model model, Principal principal) {
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

        return "Politiquedeconfidentialite";
    }


    @GetMapping("/reglement")
    public String getReglesPage(Model model, Principal principal) {
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

        return "reglement";
        
    }
    
}
