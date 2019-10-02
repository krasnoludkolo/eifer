package io.github.krasnoludkolo.resolver;

import io.vavr.collection.List;
import io.vavr.control.Either;

final class SimpleCondition<E> extends LogicalGroup<E> {

    private final Condition<E> condition;

    SimpleCondition(Condition<E> condition) {
        super(List.empty());
        this.condition = condition;
    }

    @Override
    Either<E, Success> resolve() {
        return condition.test();
    }
}
