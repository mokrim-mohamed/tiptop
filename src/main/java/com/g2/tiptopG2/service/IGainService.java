package com.g2.tiptopG2.service;

import com.g2.tiptopG2.dto.GainDto;

/**
 * Interface de service pour gérer les gains.
 * Déclare les méthodes pour les opérations CRUD et autres opérations spécifiques aux gains.
 */
public interface IGainService {
    
    /**
     * Trouve un gain par son identifiant.
     * @param id L'identifiant du gain.
     * @return Le DTO du gain correspondant.
     */
    GainDto findById(Integer id);
    
    /**
     * Trouve un gain par son code.
     * @param code Le code du gain.
     * @return Le DTO du gain correspondant.
     */
    GainDto findByCode(String code);
    
    // Méthode pour trouver tous les gains (commentée)
    // List<GainDto> findAll();
}