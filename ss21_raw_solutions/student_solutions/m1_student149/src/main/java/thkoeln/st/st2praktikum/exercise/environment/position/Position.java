package thkoeln.st.st2praktikum.exercise.environment.position;

import thkoeln.st.st2praktikum.exercise.exception.ParseException;
import thkoeln.st.st2praktikum.exercise.parser.PositionParser;

import java.util.Objects;

public class Position {

    private static final PositionParser parser = new PositionParser();

    protected int x;
    protected int y;

    protected Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(String string) throws ParseException {
        return parser.parse(string);
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toPositionString() {
        return "(" + x + "," + y + ')';
    }

    @Override
    public String toString() {
        return toPositionString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Position)) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
