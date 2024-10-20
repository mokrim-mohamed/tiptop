package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.RoleDto;

/**
 * Interface de service pour gérer les rôles.
 * Déclare les méthodes pour les opérations CRUD et autres opérations spécifiques aux rôles.
 */
public interface IRoleService {
    
    /**
     * Trouve un rôle par son identifiant.
     * @param id L'identifiant du rôle.
     * @return Le DTO du rôle correspondant.
     */
    RoleDto findById(Integer id);
    
    /**
     * Trouve un rôle par son nom.
     * @param role Le nom du rôle.
     * @return Le DTO du rôle correspondant.
     */
    RoleDto findByRole(String role);
    
    /**
     * Trouve tous les rôles.
     * @return Une liste de DTO de tous les rôles.
     */
    List<RoleDto> findAll();
}