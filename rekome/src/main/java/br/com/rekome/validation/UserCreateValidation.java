package br.com.rekome.validation;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import br.com.rekome.interfaces.ValidationInterface;
import br.com.rekome.operation.UserCreateOperation;
import br.com.rekome.utils.DateUtils;
import br.com.rekome.utils.UserUtils;

public class UserCreateValidation implements ValidationInterface {
		
	private final UserCreateOperation user;	

	public UserCreateValidation(UserCreateOperation user) {
		this.user = user;
	}

	@Override
	public void execute() {
		validateUserAge(user);
		validateUserPassword(user);
		validateIsValidPassword(user);
	}

	private void validateIsValidPassword(UserCreateOperation user) {
		var isValid = Pattern.matches(UserUtils.PASSWORD_REGEX, user.getPassword());
		if(!isValid) {
			throw new IllegalArgumentException("Password isn't valid");
		}
	}

	private void validateUserPassword(UserCreateOperation user) {
		if(!user.getPassword().equals(user.getConfirmationPassword())) {
			throw new IllegalArgumentException("Password don't match");
		}
	}

	private void validateUserAge(UserCreateOperation user) {
		var userBirthday = DateUtils.dateToLocalDate(user.getBirthday());
		var period = Period.between(userBirthday, LocalDate.now());
		
		if (period.getYears() < 18) {
	        throw new IllegalArgumentException("User must be at least 18 years old.");
	    }
	}

}
