package com.g2.tiptopG2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO (Data Transfer Object) pour les rôles.
 * Utilisée pour transférer les données des rôles entre les couches de l'application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    
    /**
     * Identifiant unique du rôle.
     */
    private Integer id;
    
    /**
     * Nom du rôle.
     */
    private String role; 
}