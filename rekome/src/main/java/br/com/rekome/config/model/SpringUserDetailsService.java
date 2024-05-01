package br.com.rekome.config.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.rekome.entities.User;
import br.com.rekome.repository.UserRepository;

public class SpringUserDetailsService implements UserDetailsService {
	
	
	private final UserRepository userRepository;
	
	public SpringUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
				
		if (user == null) {
            throw new UsernameNotFoundException("user not found!");
        }
		
        return new SpringUserDetail(user);
	}

}
