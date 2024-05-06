package br.com.rekome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rekome.operation.UserLoginOperation;
import br.com.rekome.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	
    public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginOperation login) {
    	var token = userService.login(login);
        return new ResponseEntity<>(token, HttpStatus.OK) ;
    }
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		SecurityContextHolder.getContext().setAuthentication(null);
	    SecurityContextHolder.clearContext();
		
	    return new ResponseEntity<>("User logoff.",HttpStatus.OK);
	}
    
}
