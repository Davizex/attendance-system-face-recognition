package br.com.rekome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.AppConfig;

public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

	Optional<AppConfig> findByUuid(String name);

}
