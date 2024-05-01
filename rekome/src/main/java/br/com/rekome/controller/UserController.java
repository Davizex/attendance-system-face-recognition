package br.com.rekome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rekome.operation.UserCreationOperation;
import br.com.rekome.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
		
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreationOperation user) {
        userService.create(user);
        return new ResponseEntity<>("user created", HttpStatus.CREATED) ;
    }
}
