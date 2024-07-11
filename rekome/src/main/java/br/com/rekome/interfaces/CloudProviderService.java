package br.com.rekome.interfaces;

import org.springframework.web.multipart.MultipartFile;

import br.com.rekome.entities.User;

public interface CloudProviderService {
	
	public void createUser(User createdUser, MultipartFile file) throws Exception;
	
}
