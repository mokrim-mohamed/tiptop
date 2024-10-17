package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.UserRequestDto;
import com.g2.tiptopG2.dto.UserResponseDto;

public interface IUserService {
	UserResponseDto save(UserRequestDto UserRequestDto);
	UserResponseDto findById(Integer id);
	UserResponseDto update(UserRequestDto UserRequestDto, Integer id);
	List<UserResponseDto> findAll();
}
