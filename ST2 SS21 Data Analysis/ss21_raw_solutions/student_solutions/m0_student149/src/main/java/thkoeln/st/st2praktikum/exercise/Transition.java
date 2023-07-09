package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Transition {

    private Position fromPosition;
    private Position toPosition;

    public Transition(Position fromPosition, Position toPosition) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    public Position getFromPosition() {
        return fromPosition;
    }

    public Position getToPosition() {
        return toPosition;
    }

    @Override
    public String toString() {
        return "Transition{" +
                fromPosition +
                "," + toPosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return (Objects.equals(fromPosition, that.fromPosition) && Objects.equals(toPosition, that.toPosition))
                || (Objects.equals(fromPosition, that.toPosition) && Objects.equals(toPosition, that.fromPosition));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromPosition, toPosition);
    }
}
