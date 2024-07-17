package br.com.rekome.operations;

import java.util.Date;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;

public class UserEditOperation {
	
	private final String name;
	
	@Email
	private final String email;
	
	@CPF
	private final String document;
	
	private final Date birthday;

	private final String password;
	
	private final String confirmationPassword;

	public UserEditOperation(String name, @Email String email, @CPF String document, Date birthday, String password,
			String confirmationPassword) {
		super();
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

	public String getDocument() {
		return document;
	}

	public Date getBirthday() {
		return birthday;
	} final

	public String getPassword() {
		return password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	
}
