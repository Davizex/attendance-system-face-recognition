package br.com.rekome.configs.model;

import static br.com.rekome.enums.UserRolesEnum.ADMIN;

import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.rekome.entities.User;
import br.com.rekome.operations.UserCreateOperation;
import br.com.rekome.repository.UserRepository;

@Component
public class ApplicationStart implements ApplicationListener<ContextRefreshedEvent> {
	
	private final UserRepository userRepository;
	
	public ApplicationStart(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		User userExist = userRepository.findByEmail("root@root");
		
		if(userExist == null) {	
			var userOperation = new UserCreateOperation("root", "root@root", "12345678901", new Date(), "BHU*nji9", "BHU*nji9");
			
			var user = new User(userOperation);
			user.setRole(ADMIN);
			
			this.userRepository.save(user);
		}
	}

}
