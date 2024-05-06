package br.com.rekome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rekome.operation.GroupsCreateOperation;
import br.com.rekome.service.GroupsServices;

@RestController
@RequestMapping("/groups")
public class GroupsController {
	
	private final GroupsServices groupsServices;
	
	public GroupsController(GroupsServices groupsServices) {
		this.groupsServices = groupsServices;
	}
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody GroupsCreateOperation group) {
		groupsServices.create(group);
        return new ResponseEntity<>("groupd created", HttpStatus.CREATED) ;
    }
}
