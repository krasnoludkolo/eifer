package io.github.krasnoludkolo.resolver;

import io.vavr.collection.List;
import io.vavr.control.Either;

public final class Resolver {

    private Resolver() {
    }

    public static <T> T perform(Action<T> action) {
        return action.perform();
    }

    @SafeVarargs
    public static <E> LogicalGroup<E> or(LogicalGroup<E>... logicalGroups) {
        return new Or<>(List.of(logicalGroups));
    }

    public static <E> LogicalGroup<E> or(LogicalGroup<E> logicalGroups) {
        return new Or<>(List.of(logicalGroups));
    }

    @SafeVarargs
    public static <E> LogicalGroup<E> or(Condition<E>... conditions) {
        List<LogicalGroup<E>> simpleLogicalGroups = List
                .of(conditions)
                .map(SimpleCondition::new);
        return new Or<>(simpleLogicalGroups);
    }

    public static <E> LogicalGroup<E> or(Condition<E> conditions) {
        List<LogicalGroup<E>> simpleLogicalGroups = List
                .of(conditions)
                .map(SimpleCondition::new);
        return new Or<>(simpleLogicalGroups);
    }

    public static <E> LogicalGroup<E> and(Condition<E> conditions) {
        List<LogicalGroup<E>> simpleLogicalGroups = List
                .of(conditions)
                .map(SimpleCondition::new);
        return new And<>(simpleLogicalGroups);
    }

    @SafeVarargs
    public static <E> LogicalGroup<E> and(Condition<E>... conditions) {
        List<LogicalGroup<E>> simpleLogicalGroups = List
                .of(conditions)
                .map(SimpleCondition::new);
        return new And<>(simpleLogicalGroups);
    }

    public static <E> LogicalGroup<E> and(LogicalGroup<E> logicalGroups) {
        return new And<>(List.of(logicalGroups));
    }

    @SafeVarargs
    public static <E> LogicalGroup<E> and(LogicalGroup<E>... logicalGroups) {
        return new And<>(List.of(logicalGroups));
    }

    private static <E> ResolverBuilder<E> when(List<LogicalGroup<E>> logicalGroups) {
        return new ResolverBuilder<>(logicalGroups);
    }

    @SafeVarargs
    public static <E> ResolverBuilder<E> when(Condition<E>... conditions) {
        return new ResolverBuilder<>(new And<>(conditions));
    }

    @SafeVarargs
    public static <E> ResolverBuilder<E> when(LogicalGroup<E>... logicalGroups) {
        return when(List.of(logicalGroups));
    }

    public static <E> ResolverBuilder<E> when(LogicalGroup<E> logicalGroups) {
        return when(List.of(logicalGroups));
    }

    @SafeVarargs
    public static <E> ResolverBuilder<E> when(LogicalGroup<E> logicalGroups, Condition<E>... conditions) {
        return when(List.of(logicalGroups));
    }


    public static class ResolverBuilder<E> {

        private final List<LogicalGroup<E>> logicalGroups;

        private ResolverBuilder(LogicalGroup<E> logicalGroup) {
            this.logicalGroups = List.of(logicalGroup);
        }

        private ResolverBuilder(List<LogicalGroup<E>> logicalGroups) {
            this.logicalGroups = logicalGroups;
        }

        public <T> Either<E, T> perform(Action<T> action) {
            return perform(action, logicalGroups);
        }

        @SafeVarargs
        public final ResolverBuilder<E> orAll(Condition<E>... conditions) {
            return new ResolverBuilder<>(logicalGroups.append(new And<>(conditions)));
        }

        private <T> Either<E, T> perform(Action<T> action, List<LogicalGroup<E>> logicalGroups) {
            return checkConditions(logicalGroups)
                    .map(s -> action.perform());
        }

        private Either<E, Success> checkConditions(List<LogicalGroup<E>> logicalGroup) {
            return new Or<>(logicalGroup).resolve();
        }

    }
}
