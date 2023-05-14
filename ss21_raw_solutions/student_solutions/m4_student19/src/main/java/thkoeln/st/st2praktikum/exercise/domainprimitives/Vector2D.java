package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;

@Embeddable
@Getter
@EqualsAndHashCode
public class Vector2D {

    private Integer x;
    private Integer y;

    public Vector2D() {}

    public Vector2D(Integer x, Integer y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Vector2D can't be negative.");

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

//    public Integer getX() {
//        return x;
//    }
//
//    public Integer getY() {
//        return y;
//    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public static Vector2D fromString(String vector2DString) {
        if (!vector2DString.matches("^\\([0-9]+,[0-9]+\\)$"))
            throw new IllegalArgumentException("Vector2DString doesn't match correct format. String was: " + vector2DString);

        Vector2D newVector = new Vector2D();

        String[] coordinates = vector2DString.split(",");
        newVector.x = Integer.parseInt(coordinates[0].substring(1));
        newVector.y = Integer.parseInt(coordinates[1].substring(0, coordinates[1].length() - 1));
        return newVector;
    }
    
}
