package br.com.rekome.controllers;

import org.springframework.http.HttpStatus;
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

import br.com.rekome.model.GroupDTO;
import br.com.rekome.operations.GroupsCreateOperation;
import br.com.rekome.operations.GroupsEditOperation;
import br.com.rekome.operations.InsertInGroupOperation;
import br.com.rekome.services.GroupsServices;

@RestController
@RequestMapping("/groups")
public class GroupsController {
	
	private final GroupsServices groupsServices;
	
	public GroupsController(GroupsServices groupsServices) {
		this.groupsServices = groupsServices;
	}
	
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') or  hasAuthority('SCOPE_MANAGER')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Validated GroupsCreateOperation groupOp, UriComponentsBuilder builder) {
		var group = this.groupsServices.create(groupOp);
		var uri = builder.path("/groups/" + group.getUuid()).build().toUri();
        return ResponseEntity.created(uri).body(null);
    }
	
	@GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroupDTO> findByUuid(@PathVariable final String uuid) {
		var group = this.groupsServices.findByUUID(uuid);
		return ResponseEntity.ok(new GroupDTO(group));
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> edit(@RequestBody GroupsEditOperation groupOp) {
		groupsServices.edit(groupOp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }
	
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@DeleteMapping(path = "/{uudi}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable final String uuid) {
		groupsServices.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }
	
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PostMapping(path = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertUsers(@RequestBody InsertInGroupOperation insert) {
		groupsServices.insert(insert);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }
}
