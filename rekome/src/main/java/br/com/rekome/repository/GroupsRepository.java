package br.com.rekome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.Groups;

public interface GroupsRepository extends JpaRepository<Groups, Long> {
	
	public Optional<Groups> findByUuid(String uuid);
}
