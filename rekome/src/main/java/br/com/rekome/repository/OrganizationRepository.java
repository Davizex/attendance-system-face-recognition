package br.com.rekome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	Optional<Organization> findByUuid(String organizationUuid);
}
