package br.com.rekome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.Groups;

public interface GroupsRepository extends JpaRepository<Groups, Long> {

}
