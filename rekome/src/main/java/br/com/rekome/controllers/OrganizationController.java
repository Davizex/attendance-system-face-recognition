package br.com.rekome.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rekome.operations.OrganizationCreateOperation;
import br.com.rekome.services.OrganizationService;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

	private final OrganizationService organizationService;
	
    public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}


	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrganizationCreateOperation organization) {
		this.organizationService.create(organization);
        return new ResponseEntity<>("organization created", HttpStatus.CREATED) ;
    }

}
