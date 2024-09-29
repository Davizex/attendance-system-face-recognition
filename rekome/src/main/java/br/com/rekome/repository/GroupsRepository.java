package br.com.rekome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.Group;

public interface GroupsRepository extends JpaRepository<Group, Long> {
	
	public Optional<Group> findByUuid(String uuid);
}
