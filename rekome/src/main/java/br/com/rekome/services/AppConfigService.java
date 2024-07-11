package br.com.rekome.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.rekome.entities.AppConfig;
import br.com.rekome.repository.AppConfigRepository;

@Service
public class AppConfigService {
	
	private final AppConfigRepository repository;
	
	public AppConfigService(AppConfigRepository repository) {
		this.repository = repository;
	}

	public AppConfig findByName(String name) {
		Optional<AppConfig> appConfigOp = this.repository.findByUuid(name);
		
		if(appConfigOp.isPresent()) {
			return appConfigOp.get();
		}else {
			throw new RuntimeException("Config: " + name + " não encontrado.");
		}	}

}
