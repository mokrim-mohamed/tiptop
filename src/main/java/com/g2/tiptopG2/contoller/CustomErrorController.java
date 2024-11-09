package com.g2.tiptopG2.contoller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Si l'erreur est 404, on affiche la page personnalisée
            if (statusCode == 404) {
                return "404";
            }
        }

        // Par défaut, renvoyer une page d'erreur générique si le code d'erreur n'est pas 404
        return "404";
    }
}
