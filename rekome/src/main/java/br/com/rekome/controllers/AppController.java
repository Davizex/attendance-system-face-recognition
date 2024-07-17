package br.com.rekome.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rekome.responses.AppConfigResponse;
import br.com.rekome.services.AppConfigService;

@RestController
@RequestMapping("/app")
public class AppController {

	private final AppConfigService appConfigService;
	
	public AppController(AppConfigService appConfigService) {
		this.appConfigService = appConfigService;
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PostMapping(path = "/s/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppConfigResponse> find(@PathVariable String name) {
		var appConfig = this.appConfigService.findByName(name);
		var appConfigResponse = new AppConfigResponse(appConfig);
		return new ResponseEntity<AppConfigResponse>(appConfigResponse, HttpStatus.OK) ;
    }
	
}
