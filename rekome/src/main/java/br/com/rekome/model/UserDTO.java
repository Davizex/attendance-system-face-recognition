package br.com.rekome.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.rekome.entities.User;
import br.com.rekome.enums.UserRolesEnum;

public class UserDTO {

	private String name;
	
	private String email;
	
	private String document;
	
	private LocalDate birthday;
	
	private LocalDateTime creationDate;
	
	private UserRolesEnum role;

	public UserDTO() {}

	public UserDTO(User user) {
		this.creationDate = user.getCreationDate();
		this.birthday = user.getBirthday();
		this.document = user.getDocument();
		this.email = user.getEmail();
		this.name = user.getName();
		this.role = user.getRole();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public UserRolesEnum getRole() {
		return role;
	}

	public void setRole(UserRolesEnum role) {
		this.role = role;
	}
	
}
