package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Coordinate {
    @Getter @Setter
    private Integer x;
    @Getter @Setter
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if(x < 0)
            throw new InvalidArgumentRuntimeException("x is less then 0");
        if(y < 0)
            throw new InvalidArgumentRuntimeException("y is less then 0");

        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        set(coordinateString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void set(String cordString) {
        if(cordString.toCharArray()[0] != '(' || cordString.toCharArray()[cordString.length()-1] != ')')
            throw new RuntimeException("Invalid Argument Exception");

        var rawString = cordString.substring(1 , cordString.length()-1);

        if(rawString.split(",").length != 2)
            throw new RuntimeException("Invalid Argument Exception");

        x = Integer.parseInt(rawString.split(",")[0]);
        y = Integer.parseInt(rawString.split(",")[1]);
    }

    public Integer getX() { return x; }

    public Integer getY() {
        return y;
    }
}
