package br.com.rekome.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.rekome.entities.Group;
import br.com.rekome.entities.User;
import br.com.rekome.interfaces.CloudProviderService;
import br.com.rekome.operations.UserCreateOperation;
import br.com.rekome.operations.UserEditOperation;
import br.com.rekome.operations.UserLoginOperation;
import br.com.rekome.repository.UserRepository;
import br.com.rekome.responses.LoginResponse;
import br.com.rekome.utils.EntitiesUtils;
import br.com.rekome.validations.UserCreateValidation;
import br.com.rekome.validations.UserPasswordValidation;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	private final GroupsServices groupsServices;
	
	private final JwtEncoder jwtEncoder;
	
	private final CloudProviderService provider;

	public UserService(UserRepository userRepository, GroupsServices groupsServices, JwtEncoder jwtEncoder,
			CloudProviderService provider) {
		this.userRepository = userRepository;
		this.groupsServices = groupsServices;
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
		LOGGER.debug("login request to {}", login.getEmail());
		var user = userRepository.findByEmail(login.getEmail());
		
		if(user == null) {
			throw new RuntimeException("Invalid User.");
		}
		
		new UserPasswordValidation(user, login.getPassword()).execute();;
		
		var params = EntitiesUtils.claimSet(user.getUuid(), user.getRole());	
		var value = jwtEncoder.encode(JwtEncoderParameters.from(params));
		
		LOGGER.debug("successful login to {}", login.getEmail());
		return new LoginResponse(value);
	}

	public Optional<User> getUserUuidIfInGroupUuid(String userUuid, String groupUuid) {
		Group group = groupsServices.findByUUID(groupUuid);

		return this.getUserUuidIfInGroup(userUuid, group);
	}
	
	public Optional<User> getUserUuidIfInGroup(String userUuid, Group group) {
		User user = this.findByUUID(userUuid);

		return group.getUsers().stream().filter(u -> u.getUuid().equals(user.getUuid())).findFirst();
	}
	
	public User findByUUID(String userUUID) {
		return this.userRepository.findByUuid(userUUID)
				.orElseThrow(() -> new RuntimeException("user " + userUUID + " not found."));
	}

	public void edit(UserEditOperation userOp) {
		throw new UnsupportedOperationException("User edit not implemented.");
	}

	public void delete(String uuid) {
		throw new UnsupportedOperationException("User delete not implemented.");		
	}

}