package com.g2.tiptopG2.contoller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.service.IGainService;

/**
 * Contrôleur pour gérer les requêtes liées aux gains.
 */
@Controller
@RequestMapping()
public class GainController {

    private final IGainService gainService;

    /**
     * Constructeur pour injecter le service des gains.
     * @param gainService Service pour gérer les gains.
     */
    public GainController(IGainService gainService) {
        this.gainService = gainService;
    }

    /**
     * Endpoint pour afficher la page des gains.
     * @return Le nom de la page HTML à afficher.
     */
    @GetMapping("/gain")
    public String getGainsPage() {
        return "gain";
    }

    /**
     * Endpoint pour afficher la page de mise à jour des gains.
     * @return Le nom de la page HTML à afficher.
     */
    @GetMapping("/gainUpdat")
    public String updateGain() {
        return "gainUpdate";
    }

    /**
     * Endpoint pour récupérer les gains avec clientId non null en JSON.
     * @return Une réponse HTTP contenant la liste des gains.
     */
    @GetMapping("/gain/data")
    @ResponseBody
    public ResponseEntity<List<GainDto>> getGainsWithClientIdNotNull() {
        List<GainDto> gains = gainService.findByUserIdIsNotNull();
        return ResponseEntity.ok(gains);
    }

    /**
     * Endpoint pour mettre à jour l'utilisateur associé à un gain.
     * @param gainId L'identifiant du gain.
     * @param userId L'identifiant de l'utilisateur à associer.
     * @return Une réponse HTTP contenant le gain mis à jour.
     */
    @PutMapping("/gains/{gainId}/user")
    public ResponseEntity<GainDto> updateGainUser(@PathVariable Integer gainId, @RequestParam Integer userId) {
        try {
            GainDto updatedGain = gainService.updateUser(gainId, userId);
            return ResponseEntity.ok(updatedGain);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}