package br.com.rekome.operations;

import jakarta.validation.constraints.NotNull;

public class UserLoginOperation {
	
	@NotNull
	public String email;
	
	@NotNull
	public String password;

	public UserLoginOperation(@NotNull String email, @NotNull String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
