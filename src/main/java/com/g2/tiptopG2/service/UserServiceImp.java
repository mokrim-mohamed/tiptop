package com.g2.tiptopG2.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dao.IUserDao;
import com.g2.tiptopG2.dto.UserRequestDto;
import com.g2.tiptopG2.dto.UserResponseDto;
import com.g2.tiptopG2.models.UserEntity;
@Service()
public class UserServiceImp implements IUserService {
	private IUserDao UserDao;
	private ModelMapper modelmapper;
	
	public UserServiceImp(IUserDao UserDao, ModelMapper modelmapper) {
		super();
		this.UserDao = UserDao;
		this.modelmapper = modelmapper;
	}

	@Override
	public UserResponseDto save(UserRequestDto UserRequestDto) {
		UserEntity UserEntity=modelmapper.map(UserRequestDto, UserEntity.class);
		UserEntity saved=UserDao.save(UserEntity);
		return modelmapper.map(saved, UserResponseDto.class);
	}

	@Override
	public UserResponseDto findById(Integer id) {
		UserEntity UserEntity=UserDao.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
		
		return modelmapper.map(UserEntity, UserResponseDto.class);
	}

	@Override
	public UserResponseDto update(UserRequestDto UserRequestDto, Integer id) {
		Optional<UserEntity> UserEntityOptional=UserDao.findById(id);
		if(UserEntityOptional.isPresent()) {
			UserEntity UserEntity = modelmapper.map(UserRequestDto, UserEntity.class);
			
			UserEntity updated=UserDao.save(UserEntity);
			return modelmapper.map(updated,UserResponseDto.class);
		}else {
			return null;
		}
		
	}

	@Override
	public List<UserResponseDto> findAll() {
		
		return UserDao.findAll().stream().map(el->modelmapper.map(el, UserResponseDto.class)).collect(Collectors.toList());
	}
	
	@Override
public UserResponseDto findByEmail(String email) {
    UserEntity userEntity = UserDao.findByEmail(email);
    return modelmapper.map(userEntity, UserResponseDto.class);
}
}
