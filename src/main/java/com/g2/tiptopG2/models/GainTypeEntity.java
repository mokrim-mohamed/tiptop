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
 * Classe représentant une entité type de gain dans la base de données.
 * Utilise les annotations JPA pour la persistance des données.
 */
@Entity
@Table(name = "gain_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainTypeEntity implements Serializable {

    /**
     * Identifiant unique du type de gain, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du type de gain. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String nom;

    /**
     * Liste des gains associés à ce type de gain. Relation One-to-Many avec l'entité GainEntity.
     */
    @OneToMany(mappedBy = "gainType")
    private List<GainEntity> gains;
}