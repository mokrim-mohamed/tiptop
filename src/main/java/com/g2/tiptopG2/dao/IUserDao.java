package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.tiptopG2.models.UserEntity;

/**
 * Interface de DAO (Data Access Object) pour l'entité UserEntity.
 * Utilise Spring Data JPA pour fournir des méthodes CRUD et des requêtes personnalisées.
 */
@Repository
public interface IUserDao extends JpaRepository<UserEntity, Integer> {

    /**
     * Méthode pour trouver un utilisateur par son email.
     * @param email L'email de l'utilisateur.
     * @return L'entité UserEntity correspondant à l'email donné.
     */
    UserEntity findByEmail(String email);
}