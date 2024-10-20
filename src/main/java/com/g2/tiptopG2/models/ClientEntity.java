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
 * Classe représentant une entité client dans la base de données.
 * Utilise les annotations JPA pour la persistance des données.
 */
@Entity
@Table(name = "Client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity implements Serializable {

    /**
     * Identifiant unique du client, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Nom du client. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String nom;

    /**
     * Prénom du client. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String prenom;

    /**
     * Numéro de téléphone du client. Peut être nul.
     */
    private String telephone;

    /**
     * Liste des codes gains associés à ce client. Relation One-to-Many avec l'entité CodeGaintEntity.
     */
    @OneToMany(mappedBy = "client")
    private List<CodeGaintEntity> gaints; // Utilisez CodeGaintEntity ici
}