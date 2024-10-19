package com.g2.tiptopG2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
@SpringBootApplication
public class TiptopG2Application {

	public static void main(String[] args) {
		SpringApplication.run(TiptopG2Application.class, args);
	
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
