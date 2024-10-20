package com.g2.tiptopG2.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant une entité gain dans la base de données.
 * Utilise les annotations JPA pour la persistance des données.
 */
@Entity
@Table(name = "gains")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainEntity implements Serializable {

    /**
     * Identifiant unique du gain, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Code du gain. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String code;

    /**
     * Utilisateur associé à ce gain. Relation Many-to-One avec l'entité UserEntity.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * Type de gain associé à ce gain. Relation Many-to-One avec l'entité GainTypeEntity.
     */
    @ManyToOne
    @JoinColumn(name = "gain_type_id")
    private GainTypeEntity gainType;
}