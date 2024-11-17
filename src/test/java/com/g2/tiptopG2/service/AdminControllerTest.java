package com.g2.tiptopG2.service;
import com.g2.tiptopG2.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.g2.tiptopG2.contoller.AdminController;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testGetRandomUserWithGain() throws Exception {
        // Given: Simuler un utilisateur avec un gain
        UserDto userDto = new UserDto();
        userDto.setNom("Doe");
        userDto.setPrenom("John");
        userDto.setEmail("johndoe@example.com");
        userDto.setTelephone("1234567890");

        // Simuler le comportement du service pour renvoyer un utilisateur avec un gain
        when(userService.getUsersWithGains()).thenReturn(List.of(userDto));

        mockMvc.perform(post("/randomUserWithGain")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Vérifiez que la réponse a un statut HTTP 200
                .andExpect(jsonPath("$.nom").value("Doe"))
                .andExpect(jsonPath("$.prenom").value("John"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.telephone").value("1234567890"));

        // Verify that the service method was called
        verify(userService, times(1)).getUsersWithGains();
    }

    @Test
    public void testContactezNous() throws Exception {
        // Given: Objet et message à envoyer
        String objet = "Demande d'information";
        String body = "Je souhaite en savoir plus sur vos services.";

        // Lorsque : Simuler l'appel à la méthode contactezNous du service
        mockMvc.perform(post("/contacteznous")
                .param("objet", objet)
                .param("body", body)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())  ;// Vérifiez que la réponse a un statut HTTP 200

        // Vérifier que la méthode contactezNous du service a bien été appelée
        verify(userService, times(1)).contactezNous(objet, body);
    }
}
