package com.g2.tiptopG2.service;

import com.g2.tiptopG2.dao.IUserDao;

import com.g2.tiptopG2.models.RoleEntity;
import com.g2.tiptopG2.models.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private IUserDao userDao;

    private UserEntity user;
    private UserEntity userWithGain;
    private RoleEntity role;

    @BeforeEach
    void setUp() {
        // Initialiser un utilisateur
        user = new UserEntity();
        user.setId(1);
        user.setEmail("test@example.com");


        // Initialiser un utilisateur avec un gain
        userWithGain = new UserEntity();
        userWithGain.setId(2);
        userWithGain.setEmail("testWithGain@example.com");

        // Initialiser un rôle
        role = new RoleEntity();
        role.setId(1);
        role.setRole("ROLE_USER");
    }

    @Test
    void testFindByEmail_Success() {
        // Configurer le mock pour renvoyer un utilisateur trouvé
        when(userDao.findByEmail("test@example.com")).thenReturn(user);

        // Appeler la méthode findByEmail
        UserEntity result = userDao.findByEmail("test@example.com");

        // Vérifier que le résultat est correct
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());

        // Vérifier que la méthode a été appelée une fois
        verify(userDao, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        when(userDao.findByEmail("nonexistent@example.com")).thenReturn(null);

        UserEntity result = userDao.findByEmail("nonexistent@example.com");

        assertNull(result);

        verify(userDao, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void testFindUsersWithAtLeastOneGain() {
        when(userDao.findUsersWithAtLeastOneGain()).thenReturn(Arrays.asList(userWithGain));

        List<UserEntity> result = userDao.findUsersWithAtLeastOneGain();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testWithGain@example.com", result.get(0).getEmail());

        verify(userDao, times(1)).findUsersWithAtLeastOneGain();
    }

    @Test
    void testFindByRole() {
        when(userDao.findByRole(role)).thenReturn(Arrays.asList(user));

        List<UserEntity> result = userDao.findByRole(role);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());

        verify(userDao, times(1)).findByRole(role);
    }
}

