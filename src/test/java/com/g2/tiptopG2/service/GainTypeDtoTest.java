package com.g2.tiptopG2.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.g2.tiptopG2.dto.GainTypeDto;
class GainTypeDtoTest {

    @Test
    void testGainTypeDto_ConstructorAndGettersSetters() {
        // Arrange
        Integer id = 1;
        String nom = "Gain Type A";

        // Act
        GainTypeDto gainTypeDto = new GainTypeDto(id, nom);

        // Assert
        assertEquals(1, gainTypeDto.getId());
        assertEquals("Gain Type A", gainTypeDto.getNom());
    }

    @Test
    void testGainTypeDto_NoArgsConstructor() {
        // Act
        GainTypeDto gainTypeDto = new GainTypeDto(); // Utilisation du constructeur sans arguments

        // Assert
        assertNull(gainTypeDto.getId());
        assertNull(gainTypeDto.getNom());
    }

    @Test
    void testGainTypeDto_Setters() {
        // Arrange
        GainTypeDto gainTypeDto = new GainTypeDto();
        Integer id = 2;
        String nom = "Gain Type B";

        // Act
        gainTypeDto.setId(id);
        gainTypeDto.setNom(nom);

        // Assert
        assertEquals(id, gainTypeDto.getId());
        assertEquals(nom, gainTypeDto.getNom());
    }
}