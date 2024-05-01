package br.com.rekome.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.com.rekome.entities.User;
import br.com.rekome.operation.UserCreationOperation;
import br.com.rekome.operation.UserLoginOperation;
import br.com.rekome.repository.UserRepository;
import br.com.rekome.response.LoginResponse;
import br.com.rekome.utils.UserUtils;
import br.com.rekome.validation.UserCreationValidation;
import br.com.rekome.validation.UserPasswordValidation;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	private final JwtEncoder jwtEncoder;
	
	public UserService(UserRepository userRepository, JwtEncoder jwtEncoder) {
		this.userRepository = userRepository;
		this.jwtEncoder = jwtEncoder;
	}

	public User create(UserCreationOperation userOperation) {
		
		new UserCreationValidation(userOperation).execute();
		
		var user = new User(userOperation);
		this.userRepository.save(user);
		
		return user;
	}

	public LoginResponse login(UserLoginOperation login) {
		LOGGER.debug("Solicitação de login.");
		var user = userRepository.findByEmail(login.getEmail());
		
		if(user == null) {
			throw new RuntimeException("Invalid User.");
		}
		
		new UserPasswordValidation(user, login.getPassword()).execute();;
		
		var params = UserUtils.claimSet(user.getUuid());	
		var value = jwtEncoder.encode(JwtEncoderParameters.from(params));

		return new LoginResponse(value);
	}
}