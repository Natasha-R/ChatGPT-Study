package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Point {

    private Integer x;
    private Integer y;

    public Point(){}
    public Point(Integer x, Integer y) {
        if( x<0 || y<0 )
            throw new RuntimeException();
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
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        Point p=new Point();
        String[] divideString;
        divideString = pointString.split(",");
        int x= Character.getNumericValue(pointString.charAt(1));
        int y= Character.getNumericValue(pointString.charAt(3)) ;
        if (divideString.length == 2 && 0 <= x && x <= 20 && 0 <= y && y <= 20) {
            p.setX( x );
            p.setY( y);
        } else
            throw new RuntimeException();
        return p;
    }
    
}
