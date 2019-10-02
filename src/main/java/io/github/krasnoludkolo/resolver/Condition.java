package io.github.krasnoludkolo.resolver;

import io.vavr.control.Either;

public interface Condition<E> {

    Either<E, Success> test();

}
