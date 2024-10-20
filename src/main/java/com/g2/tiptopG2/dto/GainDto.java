package com.g2.tiptopG2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO (Data Transfer Object) pour les gains.
 * Utilisée pour transférer les données des gains entre les couches de l'application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainDto {
    
    /**
     * Identifiant unique du gain.
     */
    private Integer id;
    
    /**
     * Le code du gain.
     */
    private String code;
    
    /**
     * ID de l'utilisateur qui a gagné le gain.
     */
    private Integer userId;
    
    /**
     * ID du type de gain.
     */
    private Integer gainTypeId;
}