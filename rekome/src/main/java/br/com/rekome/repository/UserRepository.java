package br.com.rekome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    User findByEmail(String email);
	
}
