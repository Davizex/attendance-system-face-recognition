package br.com.rekome.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.rekome.model.OrganizationDTO;
import br.com.rekome.operations.OrganizationCreateOperation;
import br.com.rekome.operations.OrganizationEditOperation;
import br.com.rekome.services.OrganizationService;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

	private final OrganizationService organizationService;

	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrganizationDTO> create(@RequestBody @Validated OrganizationCreateOperation organizationOp,
			UriComponentsBuilder builder) throws Exception {
		var organization = this.organizationService.create(organizationOp);
		var uri = builder.queryParam("uuid", organization.getUuid()).build().toUri();

		return ResponseEntity.created(uri).body(new OrganizationDTO(organization));
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@DeleteMapping(path = "/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable(name = "uuid", required = true) final String uuid) throws Exception {
		this.organizationService.delete(uuid);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PutMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody @Validated OrganizationEditOperation organization) throws Exception {
		this.organizationService.edit(organization);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrganizationDTO> findByUuid(@PathVariable(name = "uuid") final String uuid) throws Exception {
		var organization = this.organizationService.findByUUID(uuid);
		return ResponseEntity.ok(new OrganizationDTO(organization));
	}
}
