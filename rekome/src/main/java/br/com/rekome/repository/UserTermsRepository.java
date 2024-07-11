package br.com.rekome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.UserTerms;

public interface UserTermsRepository extends JpaRepository<UserTerms, Long> {

}
