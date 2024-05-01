package br.com.rekome.validation;

import br.com.rekome.entities.User;
import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.utils.UserUtils;
import jakarta.validation.constraints.NotNull;

public class UserPasswordValidation implements ValidationInterface {

	private final User user;
	
	private final String password;
		
	public UserPasswordValidation(@NotNull User user,@NotNull String password) {
		this.user = user;
		this.password = password;
	}
	
	@Override
	public void execute() {
		if(user == null) {
			throw new RuntimeException("Usuário é nulo");
		}
		
		if(password == null) {
			throw new RuntimeException("Senha está vázia");
		}
		
		if(!user.getPassword().equals(UserUtils.generatePassword(password, user.getSalt()))) {
			throw new RuntimeException("Senha inválida");
		}
	}
}
