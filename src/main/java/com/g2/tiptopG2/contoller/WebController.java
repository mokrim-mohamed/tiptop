package com.g2.tiptopG2.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index"; // Correspond à src/main/resources/templates/index.html pour Thymeleaf
    }
    @GetMapping("/clients")
    public String getClients() {
        return "clients"; // Correspond à src/main/resources/templates/index.html pour Thymeleaf
    }    
  
}
