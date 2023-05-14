package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Border {

    private final Coordinates start;
    private final Coordinates end;

    public Border(int startX, int startY, int endX, int endY) {
        this.start = new Coordinates(startX, startY);
        this.end = new Coordinates(endX, endY);
    }

    public Border(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Border move = (Border) o;
        return (start.equals(move.start) && end.equals(move.end)) || (start.equals(move.end) && end.equals(move.start));
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
