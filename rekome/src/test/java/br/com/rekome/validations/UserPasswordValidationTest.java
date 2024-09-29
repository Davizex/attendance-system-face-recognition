
package br.com.rekome.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rekome.entities.User;

@ExtendWith(MockitoExtension.class)
class UserPasswordValidationTest {

    @Mock
    private User user;

    @InjectMocks
    private UserPasswordValidation validation;

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        User nullUser = null;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> new UserPasswordValidation(nullUser, "password").execute());
        assertEquals("Usuário é nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        String nullPassword = null;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> new UserPasswordValidation(user, nullPassword).execute());
        assertEquals("Senha está vázia", exception.getMessage());
    }


}
