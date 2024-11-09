package com.g2.tiptopG2.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.g2.tiptopG2.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendCodeByEmail")
    public String sendEmail() {
        String code = "123456"; // Votre code de réinitialisation
        String subject = "Réinitialisation de votre mot de passe";
        String body = "Réinitialisation de votre mot de passe\n\n" +
                      "Vous avez demandé la réinitialisation de votre mot de passe.\n\n" +
                      "Voici votre code de réinitialisation : " + code + "\n\n" +
                      "Veuillez l'utiliser pour mettre à jour votre mot de passe.\n\n" +
                      "Si vous n'avez pas demandé cette réinitialisation, veuillez ignorer cet email.\n" +
                      "Merci !";
    
        
        emailService.sendEmail("mokrimmohamed2016@gmail.com", subject, body);
        return "index";
    }
}