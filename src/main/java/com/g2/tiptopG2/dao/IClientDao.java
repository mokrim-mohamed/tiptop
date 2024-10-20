package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.tiptopG2.models.ClientEntity;

/**
 * Interface de DAO (Data Access Object) pour l'entité ClientEntity.
 * Utilise Spring Data JPA pour fournir des méthodes CRUD et des requêtes personnalisées.
 */
@Repository
public interface IClientDao extends JpaRepository<ClientEntity, Integer> {

	/**
	 * Méthode pour trouver un client par son nom.
	 * @param nom Le nom du client.
	 * @return L'entité ClientEntity correspondant au nom donné.
	 */
	ClientEntity findByNom(String nom);
}