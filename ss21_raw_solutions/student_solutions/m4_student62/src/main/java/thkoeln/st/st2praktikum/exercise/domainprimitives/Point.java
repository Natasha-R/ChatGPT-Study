package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Point {

    @Setter
    private Integer x;
    @Setter
    private Integer y;


    public Point(Integer px, Integer py) {
        if(px<0||py<0){
            throw new InvalidParameterException();
        }
        x = px;
        y = py;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void updateX(int steps){
        x+=steps;
    }

    public void updateY(int steps){
        y+=steps;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        String[] positions = (pointString.substring(1,pointString.length()-1)).split(",");
        if(positions.length>2){
            throw new InvalidParameterException();
        }
         return new Point(Integer.parseInt(positions[0]),Integer.parseInt(positions[1]));
    }
    
}
