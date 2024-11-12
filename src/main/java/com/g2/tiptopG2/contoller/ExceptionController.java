package com.g2.tiptopG2.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
@Controller
public class ExceptionController {
    @GetMapping("/403")
public String accessDenied(HttpServletRequest request) {
    return "403"; // Assurez-vous que ce fichier existe dans le dossier templates
}
@GetMapping("/404")
public String introuvable() {
    return "404"; // Assurez-vous que ce fichier existe dans le dossier templates
}

}
