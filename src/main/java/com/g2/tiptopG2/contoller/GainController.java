package com.g2.tiptopG2.controller;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IGainService;
import com.g2.tiptopG2.service.IUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
@Controller
@RequestMapping()
public class GainController {

    private final IGainService gainService;
    private final IUserService userService;
    public GainController(IGainService gainService,IUserService userService) {
        this.gainService = gainService;
        this.userService= userService;
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
    // khdama f participation
    @PutMapping("/gains/gain")
    public ResponseEntity<GainDto> updateGainUser(@RequestParam Integer gainId) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User) {
            String userEmail = ((User) principal).getUsername();  // Récupérer l'email de l'utilisateur connecté
            UserDto userDto = userService.findByEmail(userEmail);  // Assurez-vous que la méthode existe dans IUserService
            GainDto updatedGain = gainService.updateUser(gainId, userDto.getId());
            
            return ResponseEntity.ok(updatedGain);
            } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
