package br.com.rekome.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.User;
import br.com.rekome.entities.UserTerms;
import br.com.rekome.enums.UserTermsEnum;

public interface UserTermsRepository extends JpaRepository<UserTerms, Long> {
	
	Collection<UserTerms> findByTermAndUser(UserTermsEnum term, User user);
}
