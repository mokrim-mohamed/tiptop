package com.g2.tiptopG2.contoller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
 

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        return "registration_page";
    }

  
}
