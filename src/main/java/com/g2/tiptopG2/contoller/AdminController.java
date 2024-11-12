package com.g2.tiptopG2.contoller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestParam;
import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.dto.Gagnant;
import org.springframework.web.bind.annotation.RequestBody;
import com.g2.tiptopG2.dto.GainTypeDto;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.service.IGainService;
import com.g2.tiptopG2.service.IUserService;

import jakarta.persistence.EntityNotFoundException;

import java.util.Random;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class AdminController {
    @Autowired
    private final IUserService userService;
    private final IGainService gainService;

    public AdminController(IUserService userService,IGainService gainService) {
        this.userService=userService;
        this.gainService=gainService;
    }


    @GetMapping("admin/dashboard")
    public String getGainsWithClientIdNotNull(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
                if (!hasUserRole) {
                    return "403";
                }
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
        return "admin/dashboard";
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
    
  
    @PostMapping("/admin/updatePassword")
    public String updatePassword(@RequestParam String currentPassword, @RequestParam String newPassword, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = ((User) authentication.getPrincipal()).getUsername();
            UserDto userDto = userService.findByEmail(userEmail);

            // Verify current password (optsional, if you have a method to do so)
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            passwordEncoder.encode(currentPassword);
            if (!passwordEncoder.equals(userDto.getMotDePasse())) {
                model.addAttribute("errorMessage", "Le mot de passe actuel est incorrect.");
                return "/admin/parrametre";
            }
            userDto.setMotDePasse(newPassword);
            userService.updateUserPassword(userDto);
            model.addAttribute("successMessage", "Mot de passe mis à jour avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue : " + e.getMessage());
        }
        return "/admin/parrametre";
    }

    @GetMapping("/admin/user")
    public String showRegistrationForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
                if (!hasUserRole) {
                    return "403";
                }
        model.addAttribute("user", new UserDto());
        return "/admin/user";  
    }

    @PostMapping("/admin/user")
    public String registerUser(@ModelAttribute("user") UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
                if (!hasUserRole) {
                    return "403";
                }
        userService.saveEmployee(userDto);
        return "redirect:/admin/user?success";
    }



    @GetMapping("/admin/ajouterEmploye")
    public String createEmp(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
        if (!hasUserRole) {
            return "403";
        }
        return "admin/createEmp";
    }

    @GetMapping("admin/jeu-concours")
    public String getJeuConcoursPage(Model model) {
        // Vérifier si un gagnant existe
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
        if (!hasUserRole) {
            return "403";
        }
        boolean hasWinner = Gagnant.email != null;
        
        // Si un gagnant existe, transmettre ses informations à la vue
        if (hasWinner) {
            model.addAttribute("winnerNom", Gagnant.nom);
            model.addAttribute("winnerPrenom", Gagnant.prenom);
            model.addAttribute("winnerEmail", Gagnant.email);
            model.addAttribute("winnerTelephone", Gagnant.telephone);
        }
    
        // Transmettre l'état de l'existence d'un gagnant
        model.addAttribute("hasWinner", hasWinner);
        return "admin/jeu-concours";
    }
    
  



    @PostMapping("/randomUserWithGain")
    @ResponseBody
    public UserDto getRandomUserWithGain() {
        if (Gagnant.email != null) {
            UserDto gagnant = new UserDto();
            gagnant.setEmail(Gagnant.email);
            gagnant.setNom(Gagnant.nom);
            gagnant.setPrenom(Gagnant.prenom);
            gagnant.setTelephone(Gagnant.telephone);
            return gagnant;
        }
    
        List<UserDto> usersWithGains = userService.getUsersWithGains();
        if (usersWithGains != null && !usersWithGains.isEmpty()) {
            Random random = new Random();
            UserDto gagnant = usersWithGains.get(random.nextInt(usersWithGains.size()));
            Gagnant.nom = gagnant.getNom();
            Gagnant.prenom = gagnant.getPrenom();
            Gagnant.email = gagnant.getEmail();
            Gagnant.telephone = gagnant.getTelephone();
    
            return gagnant;
        }    
        return null;
    }
    

    @PostMapping("/admin/updateProfile")
    @ResponseBody
    public ResponseEntity<String> updateProfile(@RequestBody UserDto updatedUserDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = null;

        if (principal instanceof User) {
            userEmail = ((User) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            userEmail = ((OAuth2User) principal).getAttribute("email");
        }

        if (userEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié.");
        }

        UserDto existingUser = userService.findByEmail(userEmail);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable.");
        }

        existingUser.setNom(updatedUserDto.getNom());
        existingUser.setEmail(updatedUserDto.getEmail());
        existingUser.setTelephone(updatedUserDto.getTelephone());
        try {
            userService.updateUserProfile(existingUser);
            return ResponseEntity.ok("Profil mis à jour avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour du profil.");
        }
    }


    @GetMapping("/admin/listeEmp")
    public String getEmp(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
        if (!hasUserRole) {
            return "403";
        }
        List<UserDto> clients = userService.getAllEmp();
        model.addAttribute("clients", clients);
        return "admin/listeEmp"; 
    }
    
    @PostMapping("/admin/ajouterEmploye")
    public String addEmployee(@ModelAttribute("userDto") UserDto userDto, Model model) {
        try {
            userService.saveEmployee(userDto);
            return "redirect:/admin/listeEmp"; 
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de l'ajout de l'employé. Veuillez réessayer.");
            return "ajouterEmploye";
        }
    }
    @GetMapping("/admin/listeClients")
    public String getClients(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasUserRole = authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"));
        if (!hasUserRole) {
            return "403";
        }
        List<UserDto> clients = userService.getAllClients();
        model.addAttribute("clients", clients);
        return "admin/listeClients"; 
    }
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("percent", 50); 
        return "test"; 
    }
    @PostMapping("/supprimer/client/{id}")
    public String deleteClient(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);  
            redirectAttributes.addFlashAttribute("successMessage", "Client supprimé avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/listeClients";  
    }
    @PostMapping("/supprimer/Emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);  
            redirectAttributes.addFlashAttribute("successMessage", "Client supprimé avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/listeEmp";  // Redirection vers la page de liste des clients
    }

    @GetMapping("/modifier/{type}/{id}")
    public String afficherModifierUtilisateur(@PathVariable String type, @PathVariable Integer id, Model model) {
        UserDto utilisateur = userService.findById(id);
        
        if (utilisateur != null) {
            model.addAttribute("client", utilisateur);
            if ("employe".equals(type)) {
                return "admin/modifierEmploye";
            } else if ("client".equals(type)) {
                return "admin/modifierClient";
            } else {
                return "redirect:/admin/listeEmp";
            }
        } else {
            return "redirect:/admin/listeEmp";
        }
    }

    @PostMapping("/modifier/{type}")
    public String modifierUtilisateur(@PathVariable String type, @ModelAttribute("client") UserDto utilisateur, RedirectAttributes redirectAttributes) {
        UserDto user = userService.findById(utilisateur.getId());
        user.setEmail(utilisateur.getEmail());
        user.setNom(utilisateur.getNom());
        user.setPrenom(utilisateur.getPrenom());
        user.setTelephone(utilisateur.getTelephone());
        userService.updateUserProfile(user);
        
        redirectAttributes.addFlashAttribute("message", "Mise à jour réussie !");
        
        // Redirection selon le type
        if ("employe".equals(type)) {
            return "redirect:/admin/listeEmp";
        } else if ("client".equals(type)) {
            return "redirect:/admin/listeClients";
        } else {
            return "redirect:/admin/listeEmp";
        }
    }



    @PostMapping("/contacteznous")
    public ResponseEntity<String> contactezNous(@RequestParam String objet, @RequestParam String body) {
        userService.contactezNous(objet, body);
        return ResponseEntity.ok("Message envoyé avec succès !");
    }
    
    

}
