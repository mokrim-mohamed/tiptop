package com.g2.tiptopG2.service;

import java.util.List;
import com.g2.tiptopG2.dto.UserDto;

public interface IUserService {
	UserDto save(UserDto UserDto);
	UserDto findById(Integer id);
	UserDto findByEmail(String nom);
	UserDto update(UserDto UserDto, Integer id);
	UserDto updateUserProfile(UserDto userDto);
    void updateUserPassword(UserDto userDto);
	List<UserDto> findAll();
	void deleteUser (Integer id);
	UserDto saveEmployee(UserDto UserDto);
	UserDto updateMdp(UserDto user);	
	UserDto saveClientAOuth(UserDto userDto);
	List<UserDto> getUsersWithGains();
	List<UserDto> getAllEmp();
	List<UserDto> getAllClients();
	public void informeGagnant();
	public void contactezNous(String object, String body);
}
