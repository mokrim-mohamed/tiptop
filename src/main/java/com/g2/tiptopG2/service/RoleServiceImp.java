package com.g2.tiptopG2.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dao.IRoleDao; // Assurez-vous que cette interface existe
import com.g2.tiptopG2.dto.RoleDto;
import com.g2.tiptopG2.models.RoleEntity;

/**
 * Implémentation du service pour gérer les rôles.
 * Utilise un DAO pour accéder aux données et un ModelMapper pour convertir les entités en DTO.
 */
@Service
public class RoleServiceImp implements IRoleService {
    private IRoleDao roleDao; // DAO pour les opérations sur les rôles
    private ModelMapper modelMapper; // Pour la conversion entre entités et DTOs

    /**
     * Constructeur pour injecter les dépendances.
     * @param roleDao DAO pour accéder aux données des rôles.
     * @param modelMapper Mapper pour convertir les entités en DTO.
     */
    public RoleServiceImp(IRoleDao roleDao, ModelMapper modelMapper) {
        this.roleDao = roleDao;
        this.modelMapper = modelMapper;
    }

    /**
     * Méthode pour trouver un rôle par son identifiant.
     * @param id L'identifiant du rôle.
     * @return Le DTO du rôle correspondant.
     * @throws RuntimeException si le rôle n'est pas trouvé.
     */
    @Override
    public RoleDto findById(Integer id) {
        RoleEntity roleEntity = roleDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    /**
     * Méthode pour trouver un rôle par son nom.
     * @param role Le nom du rôle.
     * @return Le DTO du rôle correspondant.
     * @throws RuntimeException si le rôle n'est pas trouvé.
     */
    @Override
    public RoleDto findByRole(String role) {
        RoleEntity roleEntity = roleDao.findByRole(role);
        if (roleEntity == null) {
            throw new RuntimeException("Role not found");
        }
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    /**
     * Méthode pour trouver tous les rôles.
     * @return Une liste de DTO de tous les rôles.
     */
    @Override
    public List<RoleDto> findAll() {
        return roleDao.findAll().stream()
            .map(roleEntity -> modelMapper.map(roleEntity, RoleDto.class))
            .collect(Collectors.toList());
    }
}