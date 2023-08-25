package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;

public class Barrier {
    private final Coordinate start;
    private final Coordinate end;
    private final String axis;

    public Barrier(Coordinate start, Coordinate end) {
        this(start, end, generateAxis(start, end));
    }

    public Barrier(Coordinate start, Coordinate end, String axis) {
        // Order Coordinates so start is always below or left of end
        if (start.getX() < end.getX() || start.getY() < end.getY()) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }

        this.axis = axis;
    }

    private static String generateAxis(Coordinate start, Coordinate end) {
        if (start.getX() == end.getX()) return "x";
        if (start.getY() == end.getY()) return "y";
        throw new InvalidParameterException("Diagonal Axis are not supported");
    }

    @Override
    public String toString() {
        return "Start: " + start + " End: " + end + " Axis:" + axis;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public String getAxis() {
        return axis;
    }
}
