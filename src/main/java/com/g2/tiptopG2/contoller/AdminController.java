package com.g2.tiptopG2.contoller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IUserService;

import com.g2.tiptopG2.models.UserEntity;

@Controller
public class AdminController {
    private final IUserService userService;
    public AdminController(IUserService userService) {
        this.userService=userService;
    }


    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "/admin/dashboard"; // Nom du fichier Thymeleaf
    }
    @GetMapping("/admin/user")
    public String adminUser() {
        return "/admin/user"; // Nom du fichier Thymeleaf
    }
    @GetMapping("/admin/parrametre")
    public String showSettingsPage(Model model) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = ((User) principal).getUsername();  
            UserDto userDto = userService.findByEmail(userEmail);
            model.addAttribute("user", userDto);
            return "admin/parrametre";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            return "error";
        }
    
}

}
