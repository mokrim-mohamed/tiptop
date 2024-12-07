package com.g2.tiptopG2.service;

import com.g2.tiptopG2.dao.IRoleDao;
import com.g2.tiptopG2.dto.RoleDto;
import com.g2.tiptopG2.models.RoleEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private IRoleDao roleDao;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleServiceImp roleService;

    private RoleEntity roleEntity;
    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        // Initialiser un RoleEntity et un RoleDto pour les tests
        roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setRole("ADMIN");

        roleDto = new RoleDto();
        roleDto.setId(1);
        roleDto.setRole("ADMIN");
    }

    @Test
    void testFindById_Success() {
        // Configurer le mock pour renvoyer un RoleEntity lorsqu'il est trouvé
        when(roleDao.findById(1)).thenReturn(Optional.of(roleEntity));
        when(modelMapper.map(roleEntity, RoleDto.class)).thenReturn(roleDto);

        // Appeler la méthode findById
        RoleDto result = roleService.findById(1);

        // Vérifier que le résultat est correct
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("ADMIN", result.getRole());

        // Vérifier que les méthodes ont été appelées une fois
        verify(roleDao, times(1)).findById(1);
        verify(modelMapper, times(1)).map(roleEntity, RoleDto.class);
    }

    @Test
    void testFindById_NotFound() {
        // Configurer le mock pour renvoyer un Optional.empty()
        when(roleDao.findById(2)).thenReturn(Optional.empty());

        // Vérifier que la méthode lève une exception lorsque l'ID n'est pas trouvé
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roleService.findById(2));
        assertEquals("Role not found", exception.getMessage());

        // Vérifier que le DAO a été appelé une fois
        verify(roleDao, times(1)).findById(2);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testFindByRole_Success() {
        // Configurer le mock pour renvoyer un RoleEntity lorsqu'il est trouvé
        when(roleDao.findByRole("ADMIN")).thenReturn(roleEntity);
        when(modelMapper.map(roleEntity, RoleDto.class)).thenReturn(roleDto);

        // Appeler la méthode findByRole
        RoleDto result = roleService.findByRole("ADMIN");

        // Vérifier que le résultat est correct
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("ADMIN", result.getRole());

        // Vérifier que les méthodes ont été appelées une fois
        verify(roleDao, times(1)).findByRole("ADMIN");
        verify(modelMapper, times(1)).map(roleEntity, RoleDto.class);
    }

    @Test
    void testFindByRole_NotFound() {
        // Configurer le mock pour renvoyer null lorsqu'il n'y a pas de rôle
        when(roleDao.findByRole("USER")).thenReturn(null);

        // Vérifier que la méthode lève une exception lorsque le rôle n'est pas trouvé
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roleService.findByRole("USER"));
        assertEquals("Role not found", exception.getMessage());

        // Vérifier que le DAO a été appelé une fois
        verify(roleDao, times(1)).findByRole("USER");
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testFindAll() {
        // Créer une liste de RoleEntity pour le test
        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setId(1);
        roleEntity1.setRole("ADMIN");

        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setId(2);
        roleEntity2.setRole("USER");

        List<RoleEntity> roleEntities = Arrays.asList(roleEntity1, roleEntity2);

        // Configurer le mock pour renvoyer une liste de RoleEntity
        when(roleDao.findAll()).thenReturn(roleEntities);
        when(modelMapper.map(roleEntity1, RoleDto.class)).thenReturn(new RoleDto(1, "ADMIN"));
        when(modelMapper.map(roleEntity2, RoleDto.class)).thenReturn(new RoleDto(2, "USER"));

        // Appeler la méthode findAll
        List<RoleDto> result = roleService.findAll();

        // Vérifier que le résultat est correct
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getRole());
        assertEquals("USER", result.get(1).getRole());

        // Vérifier que les méthodes ont été appelées une fois
        verify(roleDao, times(1)).findAll();
        verify(modelMapper, times(1)).map(roleEntity1, RoleDto.class);
        verify(modelMapper, times(1)).map(roleEntity2, RoleDto.class);
    }
}
