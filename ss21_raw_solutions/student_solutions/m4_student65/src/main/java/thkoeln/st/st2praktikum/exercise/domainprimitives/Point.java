package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.IllegalActionException;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;

import javax.persistence.Embeddable;


@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
@Setter
@Getter
public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        
        this.x = x;
        this.y = y;
        checkForNegative();
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
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        return string2Point(pointString);
    }
    public static Point string2Point(String string)
    {
        int index = string.indexOf("(");
        if(index != 0)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        int x = Obstacle.getNumberAt(string, index + 1);
        int checkForInvalidChars = index + String.valueOf(x).length() + 1;
        index = string.indexOf(",");
        if(index != checkForInvalidChars)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        int y = Obstacle.getNumberAt(string, index + 1);
        checkForInvalidChars = index + String.valueOf(y).length() + 1;
        index = string.indexOf(")");
        if(index != checkForInvalidChars)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        if(x < 0 || y < 0)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        return new Point(x,y);
    }
    private void checkForNegative()
    {
        if(x < 0 || y < 0)
        {
            throw new IllegalActionException("Point has to be positive values!");
        }
    }
}
