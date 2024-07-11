package br.com.rekome.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rekome.operations.GroupsCreateOperation;
import br.com.rekome.operations.InsertInGroupOperation;
import br.com.rekome.services.GroupsServices;

@RestController
@RequestMapping("/groups")
public class GroupsController {
	
	private final GroupsServices groupsServices;
	
	public GroupsController(GroupsServices groupsServices) {
		this.groupsServices = groupsServices;
	}
	
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody GroupsCreateOperation group) {
		groupsServices.create(group);
        return new ResponseEntity<>("groupd created", HttpStatus.CREATED) ;
    }
	
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PostMapping(path = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertUsers(@RequestBody InsertInGroupOperation insert) {
		groupsServices.insertIn(insert);
        return new ResponseEntity<>("groupd created", HttpStatus.CREATED) ;
    }
}
