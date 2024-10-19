package com.g2.tiptopG2.service;

import java.util.List;
import java.util.Optional;
import com.g2.tiptopG2.dto.UserRequestDto;
import com.g2.tiptopG2.dto.UserResponseDto;
import com.g2.tiptopG2.models.UserEntity;

public interface IUserService {
	UserResponseDto save(UserRequestDto UserRequestDto);
	UserResponseDto findById(Integer id);
	UserResponseDto findByEmail(String nom);
	UserResponseDto update(UserRequestDto UserRequestDto, Integer id);
	List<UserResponseDto> findAll();
}
