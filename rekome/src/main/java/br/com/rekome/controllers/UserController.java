package br.com.rekome.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.rekome.interfaces.CloudProviderService;
import br.com.rekome.operations.UserCreateOperation;
import br.com.rekome.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	private final CloudProviderService service;

    public UserController(UserService userService, CloudProviderService service) {
		this.userService = userService;
		this.service = service;
	}

	@PostMapping(path = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<?> create( 
    		@RequestParam("file") MultipartFile file,
    		@RequestPart("user") UserCreateOperation user) throws Exception {
        userService.create(user, file);
        return new ResponseEntity<>("usuário criado", HttpStatus.CREATED) ;
    }
}
