package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import org.springframework.util.StringUtils;

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
        if(x >= 0 && y >= 0) {
            this.x = x;
            this.y = y;
        }else
            throw new UnsupportedOperationException("only positive coordinates allowed");
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
        try {
            if(!(vector2DString.startsWith("(")&&vector2DString.endsWith(")")&& StringUtils.countOccurrencesOf(vector2DString, ",") == 1))
                throw new UnsupportedOperationException("wrong format");

            String[] splitCoordinates = vector2DString.replace("(","").replace(")","").split(",");

            Integer x = Integer.parseInt(splitCoordinates[0]);
            Integer y = Integer.parseInt(splitCoordinates[1]);

            if(x >= 0 && y >= 0) {
                return new Vector2D(x, y);
            }else
                throw new UnsupportedOperationException("only positive coordinates allowed");

        }catch (Exception e){
            throw new UnsupportedOperationException("wrong format");
        }
    }
    
}
