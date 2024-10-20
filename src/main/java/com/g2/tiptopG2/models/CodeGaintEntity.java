package com.g2.tiptopG2.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant une entité code gain dans la base de données.
 * Utilise les annotations JPA pour la persistance des données.
 */
@Entity
@Table(name = "CodeGaint")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeGaintEntity implements Serializable {

    /**
     * Identifiant unique du code gain. Longueur maximale de 10 caractères.
     */
    @Id
    @Column(length = 10)
    private String id;

    /**
     * Titre du code gain. Ne peut pas être nul.
     */
    @Column(nullable = false)
    private String titre;

    /**
     * Client associé à ce code gain. Relation Many-to-One avec l'entité ClientEntity.
     * Utilise le type de chargement paresseux (LAZY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;
}