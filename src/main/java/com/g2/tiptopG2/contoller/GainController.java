package com.g2.tiptopG2.controller;

import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.service.IGainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping()
public class GainController {

    private final IGainService gainService;

    public GainController(IGainService gainService) {
        this.gainService = gainService;
    }

    // Endpoint pour la vue Thymeleaf
    @GetMapping("/gain")
    public String getGainsPage() {
        return "gain";
    }

    // Endpoint pour récupérer les gains avec clientId non null en JSON
    @GetMapping("/gain/data")
    @ResponseBody
    public ResponseEntity<List<GainDto>> getGainsWithClientIdNotNull() {
        List<GainDto> gains = gainService.findByUserIdIsNotNull();
        return ResponseEntity.ok(gains);
    }
}
