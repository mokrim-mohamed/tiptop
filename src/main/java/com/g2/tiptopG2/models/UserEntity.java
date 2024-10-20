package com.g2.tiptopG2.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant une entité utilisateur dans la base de données.
 * Utilise les annotations JPA pour la persistance des données.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    /**
     * Identifiant unique de l'utilisateur, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom de l'utilisateur. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String nom;

    /**
     * Prénom de l'utilisateur. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String prenom;

    /**
     * Sexe de l'utilisateur. Peut être nul.
     */
    @Column()
    private String sexe;

    /**
     * Adresse email de l'utilisateur. Doit être unique et ne peut pas être nul.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Numéro de téléphone de l'utilisateur. Peut être nul.
     */
    @Column()
    private String telephone;

    /**
     * Mot de passe de l'utilisateur. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String motDePasse;

    /**
     * Rôle de l'utilisateur. Relation Many-to-One avec l'entité RoleEntity.
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    /**
     * Liste des gains associés à l'utilisateur. Relation One-to-Many avec l'entité GainEntity.
     * Les gains sont supprimés en cascade lorsque l'utilisateur est supprimé.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GainEntity> gains;
}