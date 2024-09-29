package br.com.rekome.validations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rekome.operations.GroupsInfoCreateOperation;

@ExtendWith(MockitoExtension.class)
class GroupsInfosValidationTest {

    @Mock
    private GroupsInfoCreateOperation groupInfo;

    @InjectMocks
    private GroupsInfosValidation validation;

    @Test
    void shouldThrowExceptionWhenAttendanceLimitIsLessThanZero() {
        when(groupInfo.getAttendanceLimit()).thenReturn(-1);

        RuntimeException exception = assertThrows(RuntimeException.class, validation::execute);
        assertEquals("Invalid attendance limit.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAttendanceLimitIsGreaterThan100() {
        when(groupInfo.getAttendanceLimit()).thenReturn(101);

        RuntimeException exception = assertThrows(RuntimeException.class, validation::execute);
        assertEquals("Invalid attendance limit.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenAttendanceLimitIsWithinValidRange() {
        when(groupInfo.getAttendanceLimit()).thenReturn(50);

        assertDoesNotThrow(validation::execute);
    }

    @Test
    void shouldNotThrowExceptionWhenAttendanceLimitIsExactlyZero() {
        when(groupInfo.getAttendanceLimit()).thenReturn(0);

        assertDoesNotThrow(validation::execute);
    }

    @Test
    void shouldNotThrowExceptionWhenAttendanceLimitIsExactly100() {
        when(groupInfo.getAttendanceLimit()).thenReturn(100);

        assertDoesNotThrow(validation::execute);
    }
}