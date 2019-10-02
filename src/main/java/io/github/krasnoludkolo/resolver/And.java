package io.github.krasnoludkolo.resolver;

import io.vavr.collection.List;
import io.vavr.control.Either;

final class And<E> extends LogicalGroup<E> {

    And(List<LogicalGroup<E>> logicalGroups) {
        super(logicalGroups);
    }

    And(Condition<E>... conditions) {
        super(List.of(conditions).map(SimpleCondition::new));
    }

    @Override
    Either<E, Success> resolve() {
        List<Either<E, Success>> errors = logicalGroups
                .map(LogicalGroup::resolve)
                .filter(Either::isLeft);
        return errors.isEmpty() ? Either.right(new Success()) : errors.head();
    }
}
