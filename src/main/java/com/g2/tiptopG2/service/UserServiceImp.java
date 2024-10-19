package com.g2.tiptopG2.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.security.auth.login.AccountNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.g2.tiptopG2.dao.IRoleDao;

import com.g2.tiptopG2.dao.IUserDao;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.dto.RoleDto;
import com.g2.tiptopG2.models.UserEntity;
@Service()
public class UserServiceImp implements IUserService {
	private IUserDao UserDao;
	private ModelMapper modelmapper;
	private IRoleDao roleDao;
	
	public UserServiceImp(IUserDao UserDao, ModelMapper modelmapper) {
		super();
		this.UserDao = UserDao;
		this.modelmapper = modelmapper;
	}



	@Override
	public UserDto findById(Integer id) {
		UserEntity UserEntity=UserDao.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
		
		return modelmapper.map(UserEntity, UserDto.class);
	}

	@Override
	public UserDto save(UserDto userDto) {
    // Encoder le mot de passe avant de sauvegarder l'utilisateur
	    UserEntity existingUser = UserDao.findByEmail(userDto.getEmail());
   		 if (existingUser != null) {
        throw new RuntimeException("Cet utilisateur avec cet email existe déjà !");
    	}
		userDto.setRoleId(3);
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	userDto.setMotDePasse(passwordEncoder.encode(userDto.getMotDePasse()));
    	UserEntity userEntity = modelmapper.map(userDto, UserEntity.class);
		//userEntity.setRole(roleDao.getById(3));
    	UserEntity saved = UserDao.save(userEntity);
    	return modelmapper.map(saved, UserDto.class);
		}

	@Override
	public List<UserDto> findAll() {
		
		return UserDao.findAll().stream().map(el->modelmapper.map(el, UserDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public UserDto findByEmail(String email) {
    UserEntity userEntity = UserDao.findByEmail(email);
    return modelmapper.map(userEntity, UserDto.class);
}
	@Override
	public UserDto update(UserDto UserDto, Integer id) {
   
    return null;
}
}
