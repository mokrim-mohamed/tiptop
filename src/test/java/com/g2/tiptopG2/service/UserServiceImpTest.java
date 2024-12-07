package com.g2.tiptopG2.service;

import com.g2.tiptopG2.dao.IRoleDao;
import com.g2.tiptopG2.dao.IUserDao;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.models.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImpTest {

    @Mock
    private IUserDao userDao;

    @Mock
    private IRoleDao roleDao;

    @Mock
    private EmailService emailService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testFindById_NotFound() {
        // Arrange
        when(userDao.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.findById(1));
    }

    @Test
    void testSave_Success() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setMotDePasse("password");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        when(userDao.findByEmail("test@example.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);
        when(userDao.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        // Act
        UserDto result = userService.save(userDto);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userDao, times(1)).findByEmail("test@example.com");
        verify(userDao, times(1)).save(userEntity);
    }

    @Test
    void testSave_EmailAlreadyExists() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("test@example.com");

        when(userDao.findByEmail("test@example.com")).thenReturn(existingUser);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.save(userDto));
    }



    @Test
    void testDeleteUser_Success() {
        // Arrange
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1);

        when(userDao.findById(1)).thenReturn(Optional.of(mockUser));

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userDao, times(1)).delete(mockUser);
    }


}
