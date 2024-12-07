package com.g2.tiptopG2.service;

import com.g2.tiptopG2.dto.GainDto;

import com.g2.tiptopG2.dto.GainTypeDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GainDtoTest {

    @Test
    void testGainDto_ConstructorAndGettersSetters() {
        // Arrange
        Integer id = 1;
        String code = "GAIN123";
        Integer userId = 1;
        GainTypeDto gainType = new GainTypeDto(); // Assumez que GainTypeDto est un DTO valide
        boolean remis = true;

        // Act
        GainDto gainDto = new GainDto(id, code, userId, gainType, remis);

        // Assert
        assertEquals(1, gainDto.getId());
        assertEquals("GAIN123", gainDto.getCode());
        assertEquals(1, gainDto.getUserId());
        assertNotNull(gainDto.getGainType());
        assertTrue(gainDto.isRemis());
    }

    @Test
    void testGainDto_NoArgsConstructor() {
        // Act
        GainDto gainDto = new GainDto(); // Utilisation du constructeur sans arguments

        // Assert
        assertNull(gainDto.getId());
        assertNull(gainDto.getCode());
        assertNull(gainDto.getUserId());
        assertNull(gainDto.getGainType());
        assertFalse(gainDto.isRemis()); // Valeur par défaut pour boolean
    }

    @Test
    void testGainDto_Setters() {
        // Arrange
        GainDto gainDto = new GainDto();
        Integer id = 1;
        String code = "GAIN456";
        Integer userId = 2;
        GainTypeDto gainType = new GainTypeDto(); // Utilisation d'un objet fictif de GainTypeDto
        boolean remis = false;

        // Act
        gainDto.setId(id);
        gainDto.setCode(code);
        gainDto.setUserId(userId);
        gainDto.setGainType(gainType);
        gainDto.setRemis(remis);

        // Assert
        assertEquals(id, gainDto.getId());
        assertEquals(code, gainDto.getCode());
        assertEquals(userId, gainDto.getUserId());
        assertEquals(gainType, gainDto.getGainType());
        assertFalse(gainDto.isRemis());
    }
}

