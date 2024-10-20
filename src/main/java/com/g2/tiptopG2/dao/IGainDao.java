package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.tiptopG2.models.GainEntity;

/**
 * Interface de DAO (Data Access Object) pour l'entité GainEntity.
 * Utilise Spring Data JPA pour fournir des méthodes CRUD et des requêtes personnalisées.
 */
@Repository
public interface IGainDao extends JpaRepository<GainEntity, Integer> {

    /**
     * Méthode pour trouver un gain par son code.
     * @param code Le code du gain.
     * @return L'entité GainEntity correspondant au code donné.
     */
    GainEntity findByCode(String code);
}