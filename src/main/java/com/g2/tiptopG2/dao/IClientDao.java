package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.tiptopG2.models.ClientEntity;
@Repository
public interface IClientDao extends JpaRepository<ClientEntity, Integer> {
	ClientEntity findByNom(String nom);
}
