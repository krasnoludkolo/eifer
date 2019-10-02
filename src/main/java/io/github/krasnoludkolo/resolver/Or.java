package io.github.krasnoludkolo.resolver;

import io.vavr.collection.List;
import io.vavr.control.Either;

final class Or<E> extends LogicalGroup<E> {

    Or(List<LogicalGroup<E>> logicalGroups) {
        super(logicalGroups);
    }

    @Override
    Either<E, Success> resolve() {
        List<Either<E, Success>> errors = logicalGroups
                .map(LogicalGroup::resolve)
                .filter(Either::isLeft);
        return errors.size() == logicalGroups.size() ? errors.head() : Either.right(new Success());
    }
}
