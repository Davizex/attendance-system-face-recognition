package br.com.rekome.validation;

import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.operation.UserCreationOperation;

public class UserCreationValidation implements ValidationInterface {
		
	private final UserCreationOperation user;	

	public UserCreationValidation(UserCreationOperation user) {
		this.user = user;
	}

	@Override
	public void execute() {
		validateUserAge(user);
		validateUserPassword(user);
	}

	private void validateUserPassword(UserCreationOperation user) {
		if(!user.getPassword().equals(user.getConfirmationPassword())) {
			throw new IllegalArgumentException("Password don't match");
		}
	}

	private void validateUserAge(UserCreationOperation user) {

	}
}
