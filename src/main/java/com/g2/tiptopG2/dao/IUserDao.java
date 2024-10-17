package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.tiptopG2.models.UserEntity;
@Repository
public interface IUserDao extends JpaRepository<UserEntity, Integer> {
}
