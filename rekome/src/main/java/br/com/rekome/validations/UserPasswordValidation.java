package br.com.rekome.validations;

import br.com.rekome.entities.User;
import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.utils.EntitiesUtils;
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
		
		if(!user.getPassword().equals(userPasswordCheck())) {
			throw new RuntimeException("Senha inválida");
		}
	}

	private String userPasswordCheck() {
		return EntitiesUtils.generatePassword(password, user.getSalt());
	}
}
