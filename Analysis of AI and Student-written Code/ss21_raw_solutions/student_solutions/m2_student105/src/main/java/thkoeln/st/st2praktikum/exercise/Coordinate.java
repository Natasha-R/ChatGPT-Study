package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if (x < 0 || y < 0) throw new RuntimeException();
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        Pattern p = Pattern.compile("\\((\\d+),(\\d+)\\)");
        Matcher matcher = p.matcher(coordinateString);

        if (matcher.matches()) {
            this.x = Integer.parseInt(matcher.group(1));
            if (this.x < 0) throw new ParseException("X needs to be positive or zero");
            this.y = Integer.parseInt(matcher.group(2));
            if (this.y < 0) throw new ParseException("Y needs to be positive or zero");

        } else throw new ParseException("Could not parse Coordinate-String");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    public Coordinate add(Integer numSteps, Direction direction) {
        try {
            switch (direction) {
                case NORTH:
                    return new Coordinate(this.x, this.y + numSteps);
                case SOUTH:
                    return new Coordinate(this.x, this.y - numSteps);
                case EAST:
                    return new Coordinate(this.x + numSteps, this.y);
                case WEST:
                    return new Coordinate(this.x - numSteps, this.y);
            }
        } catch (RuntimeException re) {
            return add(numSteps - 1, direction);
        }
        throw new RuntimeException("Unknown Direction");
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
