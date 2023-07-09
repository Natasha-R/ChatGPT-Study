package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate() {
        this(0, 0);
    }

    public Coordinate(String coordinateString) {
        // generate coordinate values from string with layout (x,y)
        Pattern coordinateStringPattern = Pattern.compile("(\\d*),(\\d*)");
        Matcher coordinateStringMatcher = coordinateStringPattern.matcher(coordinateString);

        while (coordinateStringMatcher.find()) {
            this.x = Integer.parseInt(coordinateStringMatcher.group(1));
            this.y = Integer.parseInt(coordinateStringMatcher.group(2));
        }
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void incrementX(int amount) {
        this.x += amount;
    }

    public void decrementX(int amount) {
        this.x -= amount;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incrementY(int amount) {
        this.y += amount;
    }

    public void decrementY(int amount) {
        this.y -= amount;
    }
}
