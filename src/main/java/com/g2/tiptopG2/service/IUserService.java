package com.g2.tiptopG2.service;

import java.util.List;
import java.util.Optional;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.models.UserEntity;

public interface IUserService {
	UserDto save(UserDto UserDto);
	UserDto findById(Integer id);
	UserDto findByEmail(String nom);
	UserDto update(UserDto UserDto, Integer id);
	List<UserDto> findAll();
}
