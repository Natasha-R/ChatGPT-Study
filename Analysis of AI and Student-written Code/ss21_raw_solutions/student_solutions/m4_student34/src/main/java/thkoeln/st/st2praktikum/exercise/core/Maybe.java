package thkoeln.st.st2praktikum.exercise.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.function.Function;

@Embeddable
public abstract class Maybe <T>{

    public static <T> Maybe<T> just(T value) {
        return new Just<>(value);
    }

    public static <T> Maybe<T> nothing() {
        return new Nothing<T>();
    }

    public abstract <R> Maybe<R> apply(Function<T, R> function);

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Embeddable
    public static class Just <T> extends Maybe<T>{
        @Getter
        private T value;

        @Override
        public <R> Just<R> apply(Function<T, R> function) {
            return new Maybe.Just<R>(function.apply(this.value));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Just<?> just = (Just<?>) o;
            return Objects.equals(value, just.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Embeddable
    public static class Nothing <T> extends Maybe<T>{

        public <R> Nothing<R> apply(Function<T, R> function) {
            return new Nothing<>();
        }

        @Override
        public boolean equals(Object obj) {
            return obj != null && getClass() == obj.getClass();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getClass());
        }
    }
}
