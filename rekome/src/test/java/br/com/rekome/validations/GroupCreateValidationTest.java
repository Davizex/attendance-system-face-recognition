package br.com.rekome.validations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rekome.operations.GroupsCreateOperation;

@ExtendWith(MockitoExtension.class)
class GroupCreateValidationTest {

    @Mock
    private GroupsCreateOperation group;

    @InjectMocks
    private GroupCreateValidation validator;

    @Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        when(group.getStartDate()).thenReturn(LocalDate.of(2022, 1, 1));
        when(group.getEndDate()).thenReturn(LocalDate.of(2021, 1, 1));
        
        assertThrows(RuntimeException.class, validator::execute);
    }

    @Test
    void shouldThrowExceptionWhenDurationExceedsTwoYears() {
        when(group.getStartDate()).thenReturn(LocalDate.of(2020, 1, 1));
        when(group.getEndDate()).thenReturn(LocalDate.of(2023, 1, 2));

        assertThrows(IllegalArgumentException.class, validator::execute);
    }

    @Test
    void shouldNotThrowExceptionWhenDurationIsExactlyTwoYears() {
        when(group.getStartDate()).thenReturn(LocalDate.of(2020, 1, 1));
        when(group.getEndDate()).thenReturn(LocalDate.of(2022, 1, 1));

        assertDoesNotThrow(validator::execute);
    }

    @Test
    void shouldNotThrowExceptionWhenDurationIsLessThanTwoYears() {
        when(group.getStartDate()).thenReturn(LocalDate.of(2020, 1, 1));
        when(group.getEndDate()).thenReturn(LocalDate.of(2021, 12, 31));

        assertDoesNotThrow(validator::execute);
    }
}
