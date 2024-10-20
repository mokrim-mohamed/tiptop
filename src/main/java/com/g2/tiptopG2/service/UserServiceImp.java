package com.g2.tiptopG2.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dao.IRoleDao;
import com.g2.tiptopG2.dao.IUserDao;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.models.UserEntity;

/**
 * Implémentation du service pour gérer les utilisateurs.
 * Utilise un DAO pour accéder aux données et un ModelMapper pour convertir les entités en DTO.
 */
@Service
public class UserServiceImp implements IUserService {
	private IUserDao userDao; // DAO pour les opérations sur les utilisateurs
	private ModelMapper modelMapper; // Pour la conversion entre entités et DTOs
	private IRoleDao roleDao; // DAO pour les opérations sur les rôles (non utilisé dans cette implémentation)

	/**
	 * Constructeur pour injecter les dépendances.
	 * @param userDao DAO pour accéder aux données des utilisateurs.
	 * @param modelMapper Mapper pour convertir les entités en DTO.
	 */
	public UserServiceImp(IUserDao userDao, ModelMapper modelMapper) {
		super();
		this.userDao = userDao;
		this.modelMapper = modelMapper;
	}

	/**
	 * Méthode pour trouver un utilisateur par son identifiant.
	 * @param id L'identifiant de l'utilisateur.
	 * @return Le DTO de l'utilisateur correspondant.
	 * @throws RuntimeException si l'utilisateur n'est pas trouvé.
	 */
	@Override
	public UserDto findById(Integer id) {
		UserEntity userEntity = userDao.findById(id)
			.orElseThrow(() -> new RuntimeException("User not found"));
		return modelMapper.map(userEntity, UserDto.class);
	}

	/**
	 * Méthode pour sauvegarder un nouvel utilisateur.
	 * @param userDto Le DTO de l'utilisateur à sauvegarder.
	 * @return Le DTO de l'utilisateur sauvegardé.
	 * @throws RuntimeException si un utilisateur avec le même email existe déjà.
	 */
	@Override
	public UserDto save(UserDto userDto) {
		// Vérifier si un utilisateur avec le même email existe déjà
		UserEntity existingUser = userDao.findByEmail(userDto.getEmail());
		if (existingUser != null) {
			throw new RuntimeException("Cet utilisateur avec cet email existe déjà !");
		}
		
		// Définir le rôle par défaut de l'utilisateur
		userDto.setRoleId(3);
		
		// Encoder le mot de passe avant de sauvegarder l'utilisateur
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userDto.setMotDePasse(passwordEncoder.encode(userDto.getMotDePasse()));
		
		// Convertir le DTO en entité et sauvegarder
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		UserEntity saved = userDao.save(userEntity);
		
		return modelMapper.map(saved, UserDto.class);
	}

	/**
	 * Méthode pour trouver tous les utilisateurs.
	 * @return Une liste de DTO de tous les utilisateurs.
	 */
	@Override
	public List<UserDto> findAll() {
		return userDao.findAll().stream()
			.map(el -> modelMapper.map(el, UserDto.class))
			.collect(Collectors.toList());
	}

	/**
	 * Méthode pour trouver un utilisateur par son email.
	 * @param email L'email de l'utilisateur.
	 * @return Le DTO de l'utilisateur correspondant.
	 */
	@Override
	public UserDto findByEmail(String email) {
		UserEntity userEntity = userDao.findByEmail(email);
		return modelMapper.map(userEntity, UserDto.class);
	}

	/**
	 * Méthode pour mettre à jour un utilisateur existant.
	 * @param userDto Le DTO de l'utilisateur à mettre à jour.
	 * @param id L'identifiant de l'utilisateur à mettre à jour.
	 * @return Le DTO de l'utilisateur mis à jour (actuellement non implémenté).
	 */
	@Override
	public UserDto update(UserDto userDto, Integer id) {
		// Implémentation à ajouter
		return null;
	}
}