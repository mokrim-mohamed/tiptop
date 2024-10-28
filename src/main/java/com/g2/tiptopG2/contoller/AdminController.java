package com.g2.tiptopG2.contoller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.dto.GainTypeDto;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IGainService;
import com.g2.tiptopG2.service.IUserService;

@Controller
public class AdminController {
    private final IUserService userService;
    private final IGainService gainService;
    
    public AdminController(IUserService userService,IGainService gainService) {
        this.userService=userService;
        this.gainService=gainService;
    }


    @GetMapping("/admin/dashboard")
public String getGainsWithClientIdNotNull(Model model) {
    // Collect data
    List<GainDto> gains = gainService.findByUserIdIsNotNull();
    int total =500000;
    int jouer = gains.size();
    long countNonRemis = gains.stream().filter(GainDto::isRemis).count();
    long countRemis = jouer - countNonRemis;
    double tauxRemis = (double) countRemis / jouer * 100;
    double tauxNonRemis = (double) countNonRemis / jouer * 100;

    Map<GainTypeDto, Long> gainTypeCounts = gains.stream()
        .collect(Collectors.groupingBy(GainDto::getGainType, Collectors.counting()));

    Map<GainTypeDto, Double> gainTypePercentages = gainTypeCounts.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> (double) entry.getValue() / jouer * 100
        ));

// Group by gender
Map<Boolean, List<GainDto>> gainsByGender = gains.stream()
.collect(Collectors.partitioningBy(gain -> {
    Integer userId = gain.getUserId();
    UserDto user = userService.findById(userId);
    return user != null && "H".equals(user.getSexe()); // true si homme
}));

List<GainDto> gainsForMen = gainsByGender.get(true);
List<GainDto> gainsForWomen = gainsByGender.get(false);

// Calcul des totaux pour les hommes
long countGainsForMen = gainsForMen.size();

// Calcul des totaux pour les femmes
long countGainsForWomen = gainsForWomen.size();


 Map<GainTypeDto, Long> countGainsByType = gains.stream()
        .collect(Collectors.groupingBy(GainDto::getGainType, Collectors.counting()));

    // Conversion des données en liste pour l'accès dans le template
    List<Long> gainCounts = new ArrayList<>();
    List<String> gainLabels = new ArrayList<>();
    
    for (Map.Entry<GainTypeDto, Long> entry : countGainsByType.entrySet()) {
        gainLabels.add(entry.getKey().getNom()); // Utilisez la méthode `getNom()` pour obtenir le nom
        gainCounts.add(entry.getValue());
    }
/* 
        // Group by age group
    Map<String, Long> gainsByAgeGroup = gains.stream()
    .collect(Collectors.groupingBy(gain -> {
        Integer userId = gain.getUserId();
        UserDto user = userService.findById(userId);
        if (user != null) {
            int age = calculateAge(user.getBirthDate()); // Assuming you have a birth date field
            if (age >= 18 && age <= 25) return "18-25";
            else if (age >= 26 && age <= 35) return "26-35";
            else if (age >= 36 && age <= 45) return "36-45";
            else if (age >= 46 && age <= 55) return "46-55";
            else return "56 and above";
        }
        return "Unknown";
    }, Collectors.counting()));
*/
    // Add all statistics to the model
    model.addAttribute("totalGains", jouer);
    model.addAttribute("tauxRemis", tauxRemis);
    model.addAttribute("tauxNonRemis", tauxNonRemis);
    model.addAttribute("gainTypePercentages", gainTypePercentages);
    model.addAttribute("total", total);
    model.addAttribute("countRemis", countRemis);
    model.addAttribute("countGainsForMen", countGainsForMen);
    model.addAttribute("countGainsForWomen", countGainsForWomen);
    model.addAttribute("countGainsByType", gainCounts);
    model.addAttribute("gainLabels", gainLabels);
    // Return the dashboard view
    return "/admin/dashboard";
}

    @GetMapping("/admin/user")
    public String adminUser() {
        return "/admin/user"; // Nom du fichier Thymeleaf
    }
    @GetMapping("/admin/parrametre")
    public String showSettingsPage(Model model) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = ((User) principal).getUsername();  
            UserDto userDto = userService.findByEmail(userEmail);
            model.addAttribute("user", userDto);
            return "admin/parrametre";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
            return "error";
        }
    
}

}
