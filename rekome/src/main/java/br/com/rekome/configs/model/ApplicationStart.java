package br.com.rekome.configs.model;

import java.time.LocalDate;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.rekome.entities.User;
import br.com.rekome.enums.UserRolesEnum;
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
			var userOperation = new UserCreateOperation("root", "root@root", "12345678901", LocalDate.now(), "root", "root");
			
			var user = new User(userOperation);
			user.setRole(UserRolesEnum.ROLE_ADMIN);
			
			this.userRepository.save(user);
		}
	}

}
