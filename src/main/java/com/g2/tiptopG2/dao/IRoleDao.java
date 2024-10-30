package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.tiptopG2.models.RoleEntity;
@Repository
public interface IRoleDao extends JpaRepository<RoleEntity, Integer> {
	 RoleEntity findByRole(String role);
}