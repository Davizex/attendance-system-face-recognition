package br.com.rekome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    User findByEmail(String email);
    
    Optional<User> findByUuid(String uuid);

}
