package io.github.krasnoludkolo.eifer;

import io.vavr.control.Either;

public interface Condition<E> {

    Either<E, Success> test();

}
