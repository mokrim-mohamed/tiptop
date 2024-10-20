package com.g2.tiptopG2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO (Data Transfer Object) pour les types de gains.
 * Utilisée pour transférer les données des types de gains entre les couches de l'application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainTypeDto {
    
    /**
     * Identifiant unique du type de gain.
     */
    private Integer id;
    
    /**
     * Description du type de gain.
     */
    private String description;
}