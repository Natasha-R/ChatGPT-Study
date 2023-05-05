package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


@EqualsAndHashCode
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

    public void setY(Integer y) {
        this.y = y;
    }

    public void setX(Integer x) {
        this.x = x;
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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        if (!coordinateString.startsWith("(") || !coordinateString.contains(",") || !coordinateString.endsWith(")")) {


            throw new RuntimeException("Something went wrong! Please check your input.");
        }

        Coordinate coordinate = new Coordinate();

        String[] temp = coordinateString.split(",", 2);
        String firstReplace = temp[0].replace("(", "");
        String secondReplace = temp[1].replace(")", "");
        try {
            coordinate.x = Integer.parseInt(firstReplace);
            coordinate.y = Integer.parseInt(secondReplace);
        } catch (NumberFormatException e) {
            throw new RuntimeException("One of your input does not contain numbers!");
        }
        if (coordinate.x < 0 || coordinate.y < 0) {
            throw new RuntimeException("Your input has to be bigger than 0!");
        }
        return coordinate;
    }
    
}
