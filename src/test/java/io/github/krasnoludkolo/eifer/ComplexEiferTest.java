package io.github.krasnoludkolo.eifer;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static io.github.krasnoludkolo.eifer.Eifer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexEiferTest {

    private final static Action<Integer> RETURN_ONE = () -> 1;
    private final static Condition<Error> TRUE = () -> Either.right(new Success());
    private final static Condition<Error> FALSE = () -> Either.left(new Error());

    @Test
    void shouldDoActionWithTrueConditions() {
        int result = when(
                or(
                        TRUE,
                        FALSE
                )
        ).perform(RETURN_ONE).get();

        assertEquals(1, result);
    }

    @Test
    void shouldDoActionWithTrueConditions2() {
        int result = when(
                and(
                        or(
                                TRUE,
                                FALSE
                        ),
                        and(
                                TRUE,
                                TRUE
                        )
                )
        ).perform(RETURN_ONE).get();

        assertEquals(1, result);
    }

    @Test
    void shouldDoActionWithTrueConditions3() {
        int result = when(
                TRUE,
                TRUE
        ).orAll(
                TRUE
        ).perform(RETURN_ONE).get();

        assertEquals(1, result);
    }

    @Test
    void shouldNotDoActionWithFalseConditions() {
        Either<Error, Integer> result = when(
                and(
                        TRUE,
                        FALSE
                )
        ).perform(RETURN_ONE);

        assertTrue(result.isLeft());
    }


    private static class Error {
    }
}