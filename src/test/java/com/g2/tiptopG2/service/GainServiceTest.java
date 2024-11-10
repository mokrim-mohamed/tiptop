package com.g2.tiptopG2.service;

import com.g2.tiptopG2.dao.IGainDao;
import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.models.GainEntity;
import com.g2.tiptopG2.models.UserEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GainServiceTest {

    @Mock
    private IGainDao gainDao;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GainServiceImp gainService; // Remplace IGainService par l'implémentation

    private GainEntity gainEntity;
    private GainDto gainDto;

    @BeforeEach
    void setUp() {
        // Initialiser un GainEntity et un GainDto pour les tests
        gainEntity = new GainEntity();
        gainEntity.setId(1);
        gainEntity.setCode("GAIN123");
        UserEntity user =new UserEntity();
        user.setId(10);
        gainEntity.setUser(user); // <-- Ajouter cette ligne
        gainEntity.setRemis(false);
    
        gainDto = new GainDto();
        gainDto.setId(1);
        gainDto.setCode("GAIN123");
        gainDto.setUserId(10); // <-- Ajouter cette ligne
        gainDto.setRemis(false);
    }
    
    @Test
    void testFindById_Success() {
        // Configurer le mock pour renvoyer un GainEntity lorsqu'il est trouvé
        when(gainDao.findById(1)).thenReturn(Optional.of(gainEntity));
        when(modelMapper.map(gainEntity, GainDto.class)).thenReturn(gainDto);

        // Appeler la méthode findById
        GainDto result = gainService.findById(1);

        // Vérifier que le résultat est correct
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("GAIN123", result.getCode());
        assertEquals(10, result.getUserId());
        assertFalse(result.isRemis());

        // Vérifier que les méthodes ont été appelées une fois
        verify(gainDao, times(1)).findById(1);
        verify(modelMapper, times(1)).map(gainEntity, GainDto.class);
    }

    @Test
    void testFindById_NotFound() {
        // Configurer le mock pour renvoyer un Optional.empty()
        when(gainDao.findById(2)).thenReturn(Optional.empty());

        // Vérifier que la méthode lève une exception lorsque l'ID n'est pas trouvé
        RuntimeException exception = assertThrows(RuntimeException.class, () -> gainService.findById(2));
        assertEquals("Gain not found", exception.getMessage());

        // Vérifier que le DAO a été appelé une fois
        verify(gainDao, times(1)).findById(2);
        verifyNoInteractions(modelMapper);
    }
}
