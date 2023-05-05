package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Brick {

    private final Vector2D start;
    private final Vector2D end;

    public Brick(int startX, int startY, int endX, int endY) {
        this.start = new Vector2D(startX, startY);
        this.end = new Vector2D(endX, endY);
    }

    public Brick(Vector2D start, Vector2D end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brick move = (Brick) o;
        return (start.equals(move.start) && end.equals(move.end)) || (start.equals(move.end) && end.equals(move.start));
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
