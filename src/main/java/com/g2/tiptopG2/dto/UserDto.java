package com.g2.tiptopG2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO (Data Transfer Object) pour les utilisateurs.
 * Utilisée pour transférer les données des utilisateurs entre les couches de l'application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    /**
     * Identifiant unique de l'utilisateur.
     */
    private Integer id;
    
    /**
     * Nom de l'utilisateur.
     */
    private String nom;
    
    /**
     * Prénom de l'utilisateur.
     */
    private String prenom;
    
    /**
     * Sexe de l'utilisateur.
     */
    private String sexe;
    
    /**
     * Email de l'utilisateur.
     */
    private String email;
    
    /**
     * Numéro de téléphone de l'utilisateur.
     */
    private String telephone;
    
    /**
     * Identifiant du rôle de l'utilisateur.
     */
    private Integer roleId;
    
    /**
     * Mot de passe de l'utilisateur.
     */
    private String motDePasse;
}