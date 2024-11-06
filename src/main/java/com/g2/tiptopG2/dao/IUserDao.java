package com.g2.tiptopG2.dao;
import java.util.List; // Import nécessaire


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.g2.tiptopG2.models.RoleEntity;
import com.g2.tiptopG2.models.UserEntity;
@Repository
public interface IUserDao extends JpaRepository<UserEntity, Integer> {
	UserEntity findByEmail(String nom);

	@Query("SELECT u FROM UserEntity u WHERE u.gains IS NOT EMPTY")
    List<UserEntity> findUsersWithAtLeastOneGain();
	List<UserEntity> findByRole(RoleEntity role);

}
