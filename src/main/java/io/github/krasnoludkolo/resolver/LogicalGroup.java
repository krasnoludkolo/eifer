package io.github.krasnoludkolo.resolver;

import io.vavr.collection.List;
import io.vavr.control.Either;

public abstract class LogicalGroup<E> {

    List<LogicalGroup<E>> logicalGroups;

    LogicalGroup(List<LogicalGroup<E>> logicalGroups) {
        this.logicalGroups = logicalGroups;
    }

    abstract Either<E, Success> resolve();

}
