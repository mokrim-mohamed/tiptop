package com.g2.tiptopG2.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.ArrayList;

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

    @GetMapping("/gainUpdate")
    public String getGainsPageTest() {
        return "gainUpdate";
    }
    @GetMapping("/emp")
    public String getEmpPage() {
        return "Emp";
    }
    // hadi dyakl test
    @GetMapping("/participer")
    public String updateGain() {
        return "participer";
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
    public ResponseEntity<GainDto> updateGainUser(@RequestParam String gainCode) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail;
            UserDto userDto=new UserDto();
    
            if (principal instanceof User) {
                // Utilisateur authentifié via une méthode classique
                userEmail = ((User) principal).getUsername();
                userDto = userService.findByEmail(userEmail);
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                userEmail = oauthUser.getAttribute("email");
                String name = oauthUser.getAttribute("name");
                userDto=userService.findByEmail(userEmail);
                if(userDto==null){
                userDto = new UserDto();                   
                userDto.setEmail(userEmail);
                userDto.setNom(name);
                userDto.setRoleId(3);
                userDto=userService.saveClientAOuth(userDto);   
            }             
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
    
            if(gainService.findByCodeAndUserIsNull(gainCode)==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            GainDto updatedGain = gainService.updateUser(gainCode, userDto.getId());
            return ResponseEntity.ok(updatedGain);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/getUserGains")
    public ResponseEntity<List<GainDto>> getUserGains(@RequestParam("email") String email) {
        UserDto userDto = userService.findByEmail(email);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);  // Utilisateur non trouvé
        }
        //System.out.println("Email reçu: " + userDto.getEmail() );

        List<GainDto> gains = gainService.findByUserId(userDto.getId()); 
        if (gains == null || gains.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .body(null);  
        }
        //System.out.println("gain trouve"  );

        return ResponseEntity.ok(gains);
    }



    @GetMapping("/client/historique-gains")
    public String getHistoriqueByUserId(Model model) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<GainDto> updatedGain = new ArrayList<>(); // Liste vide par défaut
            UserDto userDto=null;
            if (principal instanceof User) {
                String userEmail = ((User) principal).getUsername();  
                userDto = userService.findByEmail(userEmail);
                
                if (userDto != null) {
                    updatedGain = gainService.findByUserId(userDto.getId());
                } else {
                    model.addAttribute("errorMessage", "Utilisateur non trouvé");
                }
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                String userEmail = oauthUser.getAttribute("email");
                userDto = userService.findByEmail(userEmail);
                if (userDto != null) {
                    updatedGain = gainService.findByUserId(userDto.getId());
                } else {
                    model.addAttribute("errorMessage", "Utilisateur non trouvé");
                }
            } else {
                return "redirect:/login";  // Redirige vers la page de login si l'utilisateur n'est pas authentifié
            }
    
            // Ajoute la liste des gains (vide ou remplie)
            model.addAttribute("gains", updatedGain);
            return "client/historique-gains";  // Affiche la page historique avec les gains
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            return "error";
        }
    }
    @PostMapping("/employee/setRemis")
    public ResponseEntity<GainDto> getUserGains(@RequestParam("gainId") Integer id) {
        GainDto gainDto = gainService.findById(id);
        if (gainDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);  // Utilisateur non trouvé
        }
        gainDto = gainService.updateRemis(id);
        return ResponseEntity.ok(gainDto);
    }

}
