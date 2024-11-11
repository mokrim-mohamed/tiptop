package com.g2.tiptopG2.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.g2.tiptopG2.dao.IRoleDao;
import com.g2.tiptopG2.dao.IUserDao;
import com.g2.tiptopG2.dto.UserDto;
import com.g2.tiptopG2.models.RoleEntity;
import com.g2.tiptopG2.models.UserEntity;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import java.util.Collections;
@Service()
public class UserServiceImp implements IUserService {
	private IUserDao UserDao;
	private ModelMapper modelmapper;
	private IRoleDao roleDao;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	public UserServiceImp(IUserDao UserDao, ModelMapper modelmapper, EmailService emailService,PasswordEncoder passwordEncoder, IRoleDao roleDao) {
		super();
		this.UserDao = UserDao;
		this.modelmapper = modelmapper;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
		this.roleDao=roleDao;


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

		@Override
		public UserDto updateUserProfile(UserDto userDto) {
			UserEntity userEntity = modelmapper.map(userDto, UserEntity.class);
			UserEntity savedEntity = UserDao.save(userEntity);
			return modelmapper.map(savedEntity, UserDto.class);
		}
	
	@Override
	public List<UserDto> getUsersWithGains() {
		System.out.println("appel methode");
		List<UserEntity> usersEntities = UserDao.findUsersWithAtLeastOneGain();
		if(usersEntities.isEmpty()){
		System.out.println("null");
			return null;
		}else{
			System.out.println("n est pas null");
			return usersEntities.stream().map(el->modelmapper.map(el, UserDto.class)).collect(Collectors.toList());
		}
	}

	@Override
    public void updateUserPassword(UserDto userDto) {
        UserEntity userEntity = UserDao.findByEmail(userDto.getEmail());
        if (userEntity != null && userEntity.getMotDePasse() != null) {
            userEntity.setMotDePasse(userDto.getMotDePasse());
            UserDao.save(userEntity);  // Save updated password
        }
    }

	@Override
	public void deleteUser(Integer id) {
		Optional<UserEntity> userEntityOptional = UserDao.findById(id);
		if (userEntityOptional.isPresent()) {
			UserEntity userEntity = userEntityOptional.get();
			UserDao.delete(userEntity);
		} else {
			// Lancer une exception ou gérer l'absence de l'utilisateur
			throw new EntityNotFoundException("L'utilisateur avec l'ID " + id + " n'existe pas.");
		}
	}


	@Override
	public UserDto saveEmployee (UserDto userDto) {
	// Encoder le mot de passe avant de sauvegarder l'utilisateur
		UserEntity existingUser = UserDao.findByEmail(userDto.getEmail());
		 if (existingUser != null) {
		throw new RuntimeException("Cet utilisateur avec cet email existe déjà !");
		}
		userDto.setRoleId(2);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userDto.setMotDePasse(passwordEncoder.encode(userDto.getMotDePasse()));
		UserEntity userEntity = modelmapper.map(userDto, UserEntity.class);
		UserEntity saved = UserDao.save(userEntity);
		return modelmapper.map(saved, UserDto.class);
		}

		@Override
		public List<UserDto> getAllEmp() {
			RoleEntity role = roleDao.findById(2).orElse(null); 
			if (role == null) {
				return Collections.emptyList(); 
			}
			
			List<UserEntity> clients = UserDao.findByRole(role); 
			return clients.isEmpty() ? Collections.emptyList() : 
				clients.stream()
					   .map(userEntity -> {
						   UserDto userDto = new UserDto();
						   userDto.setId(userEntity.getId());
						   userDto.setNom(userEntity.getNom());
						   userDto.setEmail(userEntity.getEmail());
						   userDto.setTelephone(userEntity.getTelephone());
						   userDto.setPrenom(userEntity.getPrenom());
						   return userDto;
					   })
					   .collect(Collectors.toList());
		}
		
		@Override
		public List<UserDto> getAllClients() {
			RoleEntity role = roleDao.findById(3).orElse(null); 
			if (role == null) {
				return Collections.emptyList(); 
			}
			List<UserEntity> clients = UserDao.findByRole(role); 
			return clients.isEmpty() ? Collections.emptyList() : 
				clients.stream()
					   .map(userEntity -> {
						   UserDto userDto = new UserDto();
						   userDto.setId(userEntity.getId());
						   userDto.setNom(userEntity.getNom());
						   userDto.setEmail(userEntity.getEmail());
						   userDto.setTelephone(userEntity.getTelephone());
						   userDto.setPrenom(userEntity.getPrenom());
						   return userDto;
					   })
					   .collect(Collectors.toList());
		}

	}
