package br.com.rekome.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.rekome.entities.User;
import br.com.rekome.model.UserDTO;
import br.com.rekome.operations.UserCreateOperation;
import br.com.rekome.operations.UserEditOperation;
import br.com.rekome.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

    public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create( 
    		@RequestParam("file") MultipartFile file,
    		@RequestPart("user") @Validated UserCreateOperation userOp,
    		UriComponentsBuilder builder) throws Exception {
        User user = userService.create(userOp, file);
        return ResponseEntity.created(builder.path("/" + user.getUuid()).build().toUri()).build();
    }
	
	@GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findByUUID(@PathVariable final String uuid){
		var user = this.userService.findByUUID(uuid);
		var userDto = new UserDTO(user);
		
		return ResponseEntity.ok(userDto);
	}

	@PutMapping
	public ResponseEntity<?> edit(@RequestBody @Validated final UserEditOperation userOp){
		this.userService.edit(userOp);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable final String uuid){
		this.userService.delete(uuid);
		return ResponseEntity.notFound().build();
	}
	
}
