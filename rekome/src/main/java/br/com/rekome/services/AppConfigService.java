package br.com.rekome.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.rekome.entities.AppConfig;
import br.com.rekome.operations.AppConfigOperation;
import br.com.rekome.repository.AppConfigRepository;

@Service
public class AppConfigService {
	
	private final AppConfigRepository repository;
	
	public AppConfigService(AppConfigRepository repository) {
		this.repository = repository;
	}

	public AppConfig create(AppConfigOperation create) {
		var appConfig = new AppConfig(create);
		
		return this.repository.save(appConfig);
	}
	
	public AppConfig findByName(String name) {
		Optional<AppConfig> appConfigOp = this.repository.findByUuid(name);

		if (appConfigOp.isPresent()) {
			return appConfigOp.get();
		} else {
			throw new RuntimeException("Config: " + name + " não encontrado.");
		}
	}

	public void delete(AppConfigOperation name) {
		throw new UnsupportedOperationException("App Config delete not implemented.");
	}
}
