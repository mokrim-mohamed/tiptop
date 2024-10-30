package com.g2.tiptopG2.contoller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IUserService;

@Controller
public class EmployeeController {
    private final IUserService userService;
    public EmployeeController(IUserService userService) {
        this.userService=userService;
    }


    @GetMapping("/employee/parrametre")
    @PreAuthorize("hasRole('employee')")
    public String showSettingsPage(Model model) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = ((User) principal).getUsername();  
            UserDto userDto = userService.findByEmail(userEmail);
            model.addAttribute("user", userDto);
            return "employee/parrametre";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            return "error";
        }
    
}    // hadi lizdty nta 
    @GetMapping("/employee/historique-gains")
    @PreAuthorize("hasRole('employee')")

    public String histoGain() {
        return "/employee/historique-gains";
    }
}
