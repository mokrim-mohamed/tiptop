package com.g2.tiptopG2.service;
import com.g2.tiptopG2.dto.RoleDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoleDtoTest {

    @Test
    void testRoleDto_ConstructorAndGettersSetters() {
        // Arrange
        Integer id = 1;
        String role = "ADMIN";

        // Act
        RoleDto roleDto = new RoleDto(id, role);

        // Assert
        assertEquals(1, roleDto.getId());
        assertEquals("ADMIN", roleDto.getRole());
    }

    @Test
    void testRoleDto_NoArgsConstructor() {
        // Act
        RoleDto roleDto = new RoleDto(); // Utilisation du constructeur sans arguments

        // Assert
        assertNull(roleDto.getId());
        assertNull(roleDto.getRole());
    }

    @Test
    void testRoleDto_Setters() {
        // Arrange
        RoleDto roleDto = new RoleDto();
        Integer id = 2;
        String role = "USER";

        // Act
        roleDto.setId(id);
        roleDto.setRole(role);

        // Assert
        assertEquals(id, roleDto.getId());
        assertEquals(role, roleDto.getRole());
    }
}
