package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.UserDto;

/**
 * Interface de service pour gérer les utilisateurs.
 * Déclare les méthodes pour les opérations CRUD et autres opérations spécifiques aux utilisateurs.
 */
public interface IUserService {
	
	/**
	 * Sauvegarde un nouvel utilisateur.
	 * @param userDto Le DTO de l'utilisateur à sauvegarder.
	 * @return Le DTO de l'utilisateur sauvegardé.
	 */
	UserDto save(UserDto userDto);
	
	/**
	 * Trouve un utilisateur par son identifiant.
	 * @param id L'identifiant de l'utilisateur.
	 * @return Le DTO de l'utilisateur correspondant.
	 */
	UserDto findById(Integer id);
	
	/**
	 * Trouve un utilisateur par son email.
	 * @param email L'email de l'utilisateur.
	 * @return Le DTO de l'utilisateur correspondant.
	 */
	UserDto findByEmail(String email);
	
	/**
	 * Met à jour un utilisateur existant.
	 * @param userDto Le DTO de l'utilisateur à mettre à jour.
	 * @param id L'identifiant de l'utilisateur à mettre à jour.
	 * @return Le DTO de l'utilisateur mis à jour.
	 */
	UserDto update(UserDto userDto, Integer id);
	
	/**
	 * Trouve tous les utilisateurs.
	 * @return Une liste de DTO de tous les utilisateurs.
	 */
	List<UserDto> findAll();
}