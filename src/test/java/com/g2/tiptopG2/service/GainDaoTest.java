package com.g2.tiptopG2.service;

import com.g2.tiptopG2.dao.IGainDao;
import com.g2.tiptopG2.models.UserEntity;
import com.g2.tiptopG2.models.GainEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GainDaoTest {

    @Mock
    private IGainDao gainDao;

    private GainEntity gain;
    private GainEntity gainWithUser;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1);
        user.setEmail("test@example.com");

        gainWithUser = new GainEntity();
        gainWithUser.setCode("GAIN123");
        gainWithUser.setUser(user);
        gainWithUser.setRemis(true);
        gain = new GainEntity();
        gain.setCode("GAIN456");
        gain.setUser(null);
        gain.setRemis(false);
    }
    @Test
    void testFindByCode_Success() {
        when(gainDao.findByCode("GAIN123")).thenReturn(gainWithUser);

        GainEntity result = gainDao.findByCode("GAIN123");

        assertNotNull(result);
        assertEquals("GAIN123", result.getCode());
        assertNotNull(result.getUser());
        assertEquals("test@example.com", result.getUser().getEmail());

        verify(gainDao, times(1)).findByCode("GAIN123");
    }

    @Test
    void testFindByCode_NotFound() {
        when(gainDao.findByCode("NON_EXISTENT_CODE")).thenReturn(null);

        GainEntity result = gainDao.findByCode("NON_EXISTENT_CODE");

        assertNull(result);

        verify(gainDao, times(1)).findByCode("NON_EXISTENT_CODE");
    }

    @Test
    void testFindByUserId() {
        when(gainDao.findByUserId(1)).thenReturn(Arrays.asList(gainWithUser));

        List<GainEntity> result = gainDao.findByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("GAIN123", result.get(0).getCode());
        assertEquals("test@example.com", result.get(0).getUser().getEmail());

        verify(gainDao, times(1)).findByUserId(1);
    }

    @Test
    void testFindByUserId_IsEmpty() {
        when(gainDao.findByUserId(99)).thenReturn(Arrays.asList());

        List<GainEntity> result = gainDao.findByUserId(99);

        assertTrue(result.isEmpty());

        verify(gainDao, times(1)).findByUserId(99);
    }

    @Test
    void testFindByUserIdIsNotNull() {
        when(gainDao.findByUserIdIsNotNull()).thenReturn(Arrays.asList(gainWithUser));

        List<GainEntity> result = gainDao.findByUserIdIsNotNull();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertNotNull(result.get(0).getUser());

        verify(gainDao, times(1)).findByUserIdIsNotNull();
    }

    @Test
    void testFindByCodeAndUserIsNull() {
        when(gainDao.findByCodeAndUserIsNull("GAIN456")).thenReturn(gain);

        GainEntity result = gainDao.findByCodeAndUserIsNull("GAIN456");

        assertNotNull(result);
        assertEquals("GAIN456", result.getCode());
        assertNull(result.getUser());

        verify(gainDao, times(1)).findByCodeAndUserIsNull("GAIN456");
    }
}
