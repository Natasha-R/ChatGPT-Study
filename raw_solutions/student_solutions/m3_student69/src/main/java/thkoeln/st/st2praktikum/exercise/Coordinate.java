package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Coordinate {

    private Integer x;
    private Integer y;

    public Coordinate() {
    }

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if (x < 0)
            throw new RuntimeException("No negativ coordinates");
        if (y < 0) {
            throw new RuntimeException("No negativ coordinates");
        }
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if (!coordinateString.startsWith("(") || !coordinateString.contains(",") || !coordinateString.endsWith(")")) {


            throw new RuntimeException("Something went wrong! Please check your input.");
        }


        String[] temp = coordinateString.split(",", 2);
        String firstReplace = temp[0].replace("(", "");
        String secondReplace = temp[1].replace(")", "");
        try {
            x = Integer.parseInt(firstReplace);
            y = Integer.parseInt(secondReplace);
        } catch (NumberFormatException e) {
            throw new RuntimeException("One of your input does not contain numbers!");
        }
        if (x < 0 || y < 0) {
            throw new RuntimeException("Your input has to be bigger than 0!");
        }
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

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
