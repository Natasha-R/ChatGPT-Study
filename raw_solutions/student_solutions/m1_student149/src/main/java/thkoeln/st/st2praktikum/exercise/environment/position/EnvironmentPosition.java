package thkoeln.st.st2praktikum.exercise.environment.position;

import thkoeln.st.st2praktikum.exercise.environment.Space;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;

import java.util.Objects;

public class EnvironmentPosition extends Position {

    private final Space space;

    protected EnvironmentPosition(Space space, Position position) {
        super(position.x, position.y);
        this.space = space;
    }

    public static EnvironmentPosition of(Space space, int x, int y) {
        return new EnvironmentPosition(space, Position.of(x, y));
    }

    public static EnvironmentPosition of(Space space, Position position) {
        return new EnvironmentPosition(space, position);
    }

    public static EnvironmentPosition of(Space space, String string) throws ParseException {
        return new EnvironmentPosition(space, Position.of(string));
    }

    public Space getSpace() {
        return space;
    }

    @Override
    public boolean equals(Object o) {
        boolean position = super.equals(o);
        if (position) {
            if (o instanceof EnvironmentPosition) {
                EnvironmentPosition environmentPosition = (EnvironmentPosition) o;
                return space.equals(environmentPosition.space);
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), space);
    }

    @Override
    public String toString() {
        return space.toString() + ": " + super.toString();
    }
}
