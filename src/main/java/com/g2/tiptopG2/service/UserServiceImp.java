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
import java.security.SecureRandom;
import com.g2.tiptopG2.dao.IUserDao;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.dto.RoleDto;
import com.g2.tiptopG2.models.UserEntity;
import com.g2.tiptopG2.service.EmailService;
@Service()
public class UserServiceImp implements IUserService {
	private IUserDao UserDao;
	private ModelMapper modelmapper;
	private IRoleDao roleDao;
	private final EmailService emailService;
	public UserServiceImp(IUserDao UserDao, ModelMapper modelmapper, EmailService emailService) {
		super();
		this.UserDao = UserDao;
		this.modelmapper = modelmapper;
		this.emailService = emailService;
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
	if(userEntity ==null ){
		return null;
	}
    return modelmapper.map(userEntity, UserDto.class);
}
	@Override
	public UserDto update(UserDto UserDto, Integer id) {
   
    return null;
}    
	@Override
	public UserDto updateMdp(UserDto user) {
		UserEntity userEntity = UserDao.findByEmail(user.getEmail());
		if (userEntity == null) {
			throw new RuntimeException("User not found");
		}
		
		String mdp = RandomStringGenerator.generateRandomString(10);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userEntity.setMotDePasse(passwordEncoder.encode(mdp));
		UserEntity saved = UserDao.save(userEntity);

		String subject = "Réinitialisation de votre mot de passe";
		String body = "Réinitialisation de votre mot de passe\n\n" +
					"Voici votre code de réinitialisation : " + mdp + "\n\n" +
					"Merci !";

		// Appel à la méthode sendEmail
		emailService.sendEmail(user.getEmail(), subject, body);
		return modelmapper.map(saved, UserDto.class);
	}
	@Override
	public UserDto saveClientAOuth(UserDto userDto) {
    // Encoder le mot de passe avant de sauvegarder l'utilisateur
	    UserEntity existingUser = UserDao.findByEmail(userDto.getEmail());
   		 if (existingUser != null) {
        throw new RuntimeException("Cet utilisateur avec cet email existe déjà !");
    	}
		//userDto.setRoleId(3);
    	UserEntity userEntity = modelmapper.map(userDto, UserEntity.class);
    	UserEntity saved = UserDao.save(userEntity);
    	return modelmapper.map(saved, UserDto.class);
		}
}
