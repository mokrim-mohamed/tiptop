package com.g2.tiptopG2.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant une entité rôle dans la base de données.
 * Utilise les annotations JPA pour la persistance des données.
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity implements Serializable {

    /**
     * Identifiant unique du rôle, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du rôle. Doit être unique et ne peut pas être nul.
     */
    @Column(nullable = false, unique = true)
    private String role;

    /**
     * Liste des utilisateurs associés à ce rôle. Relation One-to-Many avec l'entité UserEntity.
     */
    @OneToMany(mappedBy = "role")
    private List<UserEntity> users;
}