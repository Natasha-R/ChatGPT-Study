package thkoeln.st.st2praktikum.exercise;

public class Border {
    Coordinate start;
    Coordinate end;
    String axis;

    public Border (Coordinate start, Coordinate end, String axis) {
        if (start.getX() < end.getX() || start.getY() < end.getY()) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }

        this.axis = axis;
    }

    @Override
    public String toString() {
        return "Start: " + start + " End: " + end + " Axis:" + axis;
    }
}
