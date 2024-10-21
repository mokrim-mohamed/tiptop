package com.g2.tiptopG2.controller;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

    // page dyal les gain gains makhdaminch biha db
    @GetMapping("/gain")
    public String getGainsPage() {
        return "gain";
    }
    // hadi dyakl test
    @GetMapping("/gainUpdat")
    public String updateGain() {
        return "gainUpdate";
    }

    //hadi bach ankhdmo admin khasha mazal tbdl bach data doz f model
    @GetMapping("/gain/data")
    @ResponseBody
    public ResponseEntity<List<GainDto>> getGainsWithClientIdNotNull() {
        List<GainDto> gains = gainService.findByUserIdIsNotNull();
        return ResponseEntity.ok(gains);
    }
    // hadi lizdty nta 
    @GetMapping("/admin/historique-gains")
    public String histoGain() {
        return "/admin/historique-gains";
    }
    // hadi test  wlkn hya bach andiro participation atmodifia chwya
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
