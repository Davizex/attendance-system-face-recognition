package br.com.rekome.validations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rekome.operations.UserCreateOperation;

@ExtendWith(MockitoExtension.class)
class UserCreateValidationTest {

	@Mock
	private UserCreateOperation user;

	@InjectMocks
	private UserCreateValidation validation;

	@Test
    void shouldThrowExceptionWhenUserIsUnder18() {
        when(user.getBirthday()).thenReturn(LocalDate.now().minusYears(17));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, validation::execute);
        assertEquals("User must be at least 18 years old.", exception.getMessage());
    }

	@Test
    void shouldThrowExceptionWhenPasswordsDoNotMatch() {
        when(user.getBirthday()).thenReturn(LocalDate.now().minusYears(18));

		when(user.getPassword()).thenReturn("password123");
        when(user.getConfirmationPassword()).thenReturn("differentPassword");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, validation::execute);
        assertEquals("Password don't match", exception.getMessage());
    }

	@Test
	void shouldThrowExceptionWhenPasswordIsNotValid() {
		when(user.getPassword()).thenReturn("weakpassword");
		when(user.getConfirmationPassword()).thenReturn("weakpassword");
	    when(user.getBirthday()).thenReturn(LocalDate.now().minusYears(18));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, validation::execute);
        assertEquals("Password isn't valid", exception.getMessage());
	}

	@Test
	void shouldNotThrowException() {
		when(user.getPassword()).thenReturn("StrongPassw0rd!");
	    when(user.getConfirmationPassword()).thenReturn("StrongPassw0rd!");
	    when(user.getBirthday()).thenReturn(LocalDate.now().minusYears(18));

	    assertDoesNotThrow(validation::execute);
	}
}
