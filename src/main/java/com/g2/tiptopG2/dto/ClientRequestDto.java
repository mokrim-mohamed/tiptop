package com.g2.tiptopG2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO (Data Transfer Object) pour les requêtes liées aux clients.
 * Utilisée pour transférer les données entre les couches de l'application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {
	
	/**
	 * Identifiant unique du client.
	 */
	private Integer id;
	
	/**
	 * Nom du client.
	 */
	private String nom;
	
	/**
	 * Prénom du client.
	 */
	private String prenom;
	
	/**
	 * Numéro de téléphone du client.
	 */
	private String telephone;
}