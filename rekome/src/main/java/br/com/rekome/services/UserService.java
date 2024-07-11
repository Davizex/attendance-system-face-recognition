package br.com.rekome.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.rekome.entities.User;
import br.com.rekome.interfaces.CloudProviderService;
import br.com.rekome.operations.UserCreateOperation;
import br.com.rekome.operations.UserLoginOperation;
import br.com.rekome.repository.UserRepository;
import br.com.rekome.responses.LoginResponse;
import br.com.rekome.utils.UserUtils;
import br.com.rekome.validations.UserCreateValidation;
import br.com.rekome.validations.UserPasswordValidation;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	private final JwtEncoder jwtEncoder;
	
	private final CloudProviderService provider;

	public UserService(UserRepository userRepository, JwtEncoder jwtEncoder, CloudProviderService provider) {
		super();
		this.userRepository = userRepository;
		this.jwtEncoder = jwtEncoder;
		this.provider = provider;
	}
	
	@Transactional
	public User create(UserCreateOperation userOperation, MultipartFile file) throws Exception {
		try {
			new UserCreateValidation(userOperation).execute();
			
			var user = new User(userOperation);
			var createdUser = this.userRepository.save(user);
			
			LOGGER.debug("Create User with provider: " + provider.getClass());
			provider.createUser(createdUser, file);
			
			return user;
		}catch (Exception e) {
			throw e;
		}
		
	}

	public LoginResponse login(UserLoginOperation login) {
		LOGGER.debug("Solicitação de login.");
		var user = userRepository.findByEmail(login.getEmail());
		
		if(user == null) {
			throw new RuntimeException("Invalid User.");
		}
		
		new UserPasswordValidation(user, login.getPassword()).execute();;
		
		var params = UserUtils.claimSet(user.getUuid(), user.getRole());	
		var value = jwtEncoder.encode(JwtEncoderParameters.from(params));

		return new LoginResponse(value);
	}

	public User findByUUID(String userUUID) {
		Optional<User> userOp = this.userRepository.findByUuid(userUUID);
		
		if(userOp.isPresent()) {
			return userOp.get();
		}else {
			throw new RuntimeException("user " + userUUID + " não encontrado.");
		}
	}

}