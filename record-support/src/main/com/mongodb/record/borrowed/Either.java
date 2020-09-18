package com.mongodb.record.borrowed;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.bson.assertions.Assertions.notNull;

final class Either<L, R> {

    public static <L, R> Either<L, R> left(final L value) {
        return new Either<>(notNull("value", value), null);
    }

    public static <L, R> Either<L, R> right(final R value) {
        return new Either<>(null, notNull("value", value));
    }

    private final L left;
    private final R right;

    private Either(final L l, final R r) {
        left = l;
        right = r;
    }

    public <T> T map(final Function<? super L, ? extends T> lFunc, final Function<? super R, ? extends T> rFunc) {
        return left != null ? lFunc.apply(left) : rFunc.apply(right);
    }

    public void apply(final Consumer<? super L> lFunc, final Consumer<? super R> rFunc) {
        if (left != null){
            lFunc.accept(left);
        }
        if (right != null){
            rFunc.accept(right);
        }
    }

    @Override
    public String toString() {
        return "Either{"
                + "left=" + left
                + ", right=" + right
                + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Either<?, ?> either = (Either<?, ?>) o;
        return Objects.equals(left, either.left) && Objects.equals(right, either.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
