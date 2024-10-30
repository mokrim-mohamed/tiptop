package com.g2.tiptopG2.contoller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('admin')")

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

// Group by gender// Group by gender and keep only one gain per user
Map<Boolean, List<GainDto>> gainsByGender = gains.stream()
.filter(gain -> {
    Integer userId = gain.getUserId();
    UserDto user = userService.findById(userId);
    return user != null; // Filtrer les gains pour les utilisateurs existants
})
.collect(Collectors.groupingBy(gain -> {
    UserDto user = userService.findById(gain.getUserId());
    return user != null && "H".equals(user.getSexe()); // true si homme
}));

List<GainDto> gainsForMen = gainsByGender.getOrDefault(true, new ArrayList<>());
List<GainDto> gainsForWomen = gainsByGender.getOrDefault(false, new ArrayList<>());

// Calcul des totaux pour les hommes (uniquement des gains uniques)
long countGainsForMen = gainsForMen.stream()
.map(GainDto::getUserId) // On utilise l'ID de l'utilisateur pour ne compter qu'une seule fois par utilisateur
.distinct() // Supprimer les doublons
.count();

// Calcul des totaux pour les femmes (uniquement des gains uniques)
long countGainsForWomen = gainsForWomen.stream()
.map(GainDto::getUserId)
.distinct()
.count();

// Group gains by type and count them (en gardant seulement un gain par utilisateur)


Map<GainTypeDto, Long> countGainsByType = gains.stream()
.collect(Collectors.groupingBy(GainDto::getGainType, Collectors.counting()));

// Conversion des données en liste pour l'accès dans le template
List<Long> gainCounts = new ArrayList<>();
List<String> gainLabels = new ArrayList<>();

for (Map.Entry<GainTypeDto, Long> entry : countGainsByType.entrySet()) {
gainLabels.add(entry.getKey().getNom()); // Utilisez la méthode `getNom()` pour obtenir le nom
gainCounts.add(entry.getValue());
}

// Group gains by age range and count them (en gardant seulement un gain par utilisateur)
Map<String, Long> gainsByAgeGroup = gains.stream()
.filter(gain -> {
    Integer userId = gain.getUserId();
    UserDto user = userService.findById(userId);
    return user != null; // Filtrer les gains pour les utilisateurs existants
})
.collect(Collectors.groupingBy(gain -> {
    Integer userId = gain.getUserId();
    UserDto user = userService.findById(userId);
    int age = (user != null) ? user.getAge() : 0;
    if (age < 18) return "Moins de 18 ans";
    else if (age <= 25) return "18-25 ans";
    else if (age <= 35) return "26-35 ans";
    else if (age <= 50) return "36-50 ans";
    else return "Plus de 50 ans";
}, Collectors.mapping(GainDto::getUserId, Collectors.toSet())))
.entrySet().stream()
.collect(Collectors.toMap(Map.Entry::getKey, entry -> (long) entry.getValue().size()));

// Prepare ageLabels and ageCounts
List<String> ageLabels = new ArrayList<>(gainsByAgeGroup.keySet());
List<Long> ageCounts = new ArrayList<>(gainsByAgeGroup.values());
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
    model.addAttribute("ageLabels", ageLabels); // Add age labels
    model.addAttribute("ageCounts", ageCounts); // Add age counts

    // Return the dashboard view
    return "/admin/dashboard";
}

    @GetMapping("/admin/user")
    @PreAuthorize("hasRole('admin')")
    public String adminUser() {
        return "/admin/user"; // Nom du fichier Thymeleaf
    }
    @GetMapping("/admin/parrametre")
    @PreAuthorize("hasRole('admin')")
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
