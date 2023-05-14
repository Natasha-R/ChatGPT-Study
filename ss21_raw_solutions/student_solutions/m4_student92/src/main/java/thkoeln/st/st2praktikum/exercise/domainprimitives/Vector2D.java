package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        if (x < 0 || y < 0) {
            throw new UnsupportedOperationException("no negative coordinates allowed");
        }
        
        this.x = x;
        this.y = y;
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
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public static Vector2D fromString(String vector2DString) {
        if (Integer.parseInt(vector2DString.substring(1,2)) < 0 || Integer.parseInt(vector2DString.substring(3,4)) < 0) {
            throw new IllegalArgumentException("no negative coordinates allowed");
        }

        if (vector2DString.length() != 5) {
            throw new IllegalArgumentException("not the correct format");
        }

        //this.x = Integer.parseInt(vector2DString.substring(1,2));
        //this.y = Integer.parseInt(vector2DString.substring(3,4));

        return new Vector2D(Integer.parseInt(vector2DString.substring(1,2)), Integer.parseInt(vector2DString.substring(3,4)));
    }
    
}
