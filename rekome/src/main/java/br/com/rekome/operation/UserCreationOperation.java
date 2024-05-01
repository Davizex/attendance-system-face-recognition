package br.com.rekome.operation;

import java.util.Date;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserCreationOperation {

	@NotBlank
	private final String name;
	
	@Email
	@NotBlank
	private final String email;
	
	@CPF
	@NotBlank
	private final String document;
	
	@NotNull
	private final Date birthday;

	@NotBlank
	private final String password;
	
	@NotBlank
	private final String confirmationPassword;

	public UserCreationOperation(@NotBlank String name, @Email @NotBlank String email, @CPF @NotBlank String document,
			@NotNull Date birthday, @NotBlank String password, @NotBlank String confirmationPassword) {
		this.name = name;
		this.email = email;
		this.document = document;
		this.birthday = birthday;
		this.password = password;
		this.confirmationPassword = confirmationPassword;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getDocument() {
		return document;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}	
	
}
