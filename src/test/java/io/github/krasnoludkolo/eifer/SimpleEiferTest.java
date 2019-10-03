package io.github.krasnoludkolo.eifer;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleEiferTest {

    private final static Action<Integer> RETURN_ONE = () -> 1;
    private final static Condition<Error> TRUE = () -> Either.right(new Success());
    private final static Condition<Error> FALSE = () -> Either.left(new Error());

    @Test
    void shouldDoActionWithoutConditions() {
        int result = Eifer.perform(RETURN_ONE);

        assertEquals(1, result);
    }

    @Test
    void shouldDoActionWithTrueCondition() {
        int result = Eifer
                .when(TRUE)
                .perform(RETURN_ONE)
                .get();

        assertEquals(1, result);
    }

    @Test
    void shouldReturnErrorWithFalseCondition() {
        Either<Error, Integer> result = Eifer
                .when(FALSE)
                .perform(RETURN_ONE);

        assertTrue(result.isLeft());
    }

    @Test
    void shouldDoActionWithAllTrueConditions() {
        int result = Eifer
                .when(
                        TRUE,
                        TRUE
                )
                .perform(RETURN_ONE)
                .get();

        assertEquals(1, result);
    }

    @Test
    void shouldReturnErrorWithFalseConditions() {
        Either<Error, Integer> result = Eifer
                .when(
                        FALSE,
                        TRUE
                )
                .perform(RETURN_ONE);

        assertTrue(result.isLeft());
    }

    @Test
    void shouldReturnErrorWithAllOrFalseConditions() {
        Either<Error, Integer> result = Eifer
                .when(
                        FALSE,
                        TRUE
                ).orAll(
                        FALSE
                ).perform(RETURN_ONE);

        assertTrue(result.isLeft());
    }


    private static class Error {
    }
}