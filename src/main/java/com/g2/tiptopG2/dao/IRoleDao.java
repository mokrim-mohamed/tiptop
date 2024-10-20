package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.tiptopG2.models.RoleEntity;

/**
 * Interface de DAO (Data Access Object) pour l'entité RoleEntity.
 * Utilise Spring Data JPA pour fournir des méthodes CRUD et des requêtes personnalisées.
 */
@Repository
public interface IRoleDao extends JpaRepository<RoleEntity, Integer> {

	/**
	 * Méthode pour trouver un rôle par son nom.
	 * @param role Le nom du rôle.
	 * @return L'entité RoleEntity correspondant au nom du rôle donné.
	 */
	RoleEntity findByRole(String role);
}